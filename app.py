from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import requests, os, io, uuid, torch, numpy as np, pydicom, cv2
from PIL import Image
from torchvision import transforms, models
from urllib.parse import urlparse, unquote
import torch.nn as nn
import torch.nn.functional as F
import firebase_admin
from firebase_admin import credentials, storage

# ───────────────────────────────────────────────────────────────
# 1) FastAPI app
# ───────────────────────────────────────────────────────────────
app = FastAPI(title="Stroke Classifier + Ischemic Segmenter")

# ───────────────────────────────────────────────────────────────
# 2) Classification model
# ───────────────────────────────────────────────────────────────
def get_wide_resnet50_2(num_classes=4, weight_path=None, device='cpu'):
    model = models.wide_resnet50_2(weights=None)
    model.conv1 = nn.Conv2d(1, 64, 7, 2, 3, bias=False)
    model.bn1   = nn.BatchNorm2d(64)
    model.fc    = nn.Linear(model.fc.in_features, num_classes)
    if weight_path:
        state = torch.load(weight_path, map_location=device)
        model.load_state_dict(state)
    return model.to(device).eval()

DEVICE    = 'cuda' if torch.cuda.is_available() else 'cpu'
MODEL_CLS = get_wide_resnet50_2(4, weight_path="last_best.pth", device=DEVICE)

# ───────────────────────────────────────────────────────────────
# 3) UNet segmentation model
# ───────────────────────────────────────────────────────────────
class DoubleConv(nn.Module):
    def __init__(self, in_ch, out_ch, mid_ch=None):
        super().__init__()
        mid_ch = mid_ch or out_ch
        self.double_conv = nn.Sequential(
            nn.Conv2d(in_ch, mid_ch, 3, padding=1, bias=False),
            nn.InstanceNorm2d(mid_ch),
            nn.ReLU(inplace=True),
            nn.Conv2d(mid_ch, out_ch, 3, padding=1, bias=False),
            nn.InstanceNorm2d(out_ch),
            nn.ReLU(inplace=True),
        )
    def forward(self, x): return self.double_conv(x)

class Down(nn.Module):
    def __init__(self, in_ch, out_ch):
        super().__init__()
        self.maxpool_conv = nn.Sequential(
            nn.MaxPool2d(2),
            DoubleConv(in_ch, out_ch)
        )
    def forward(self, x): return self.maxpool_conv(x)

class Up(nn.Module):
    def __init__(self, in_ch, out_ch, bilinear=True):
        super().__init__()
        if bilinear:
            self.up   = nn.Upsample(scale_factor=2, mode='bilinear', align_corners=True)
            self.conv = DoubleConv(in_ch, out_ch, in_ch//2)
        else:
            self.up   = nn.ConvTranspose2d(in_ch, in_ch//2, 2, stride=2)
            self.conv = DoubleConv(in_ch, out_ch)
    def forward(self, x1, x2):
        x1 = self.up(x1)
        diffY = x2.size(2) - x1.size(2)
        diffX = x2.size(3) - x1.size(3)
        x1 = F.pad(x1, [diffX//2, diffX-diffX//2, diffY//2, diffY-diffY//2])
        return self.conv(torch.cat([x2, x1], dim=1))

class OutConv(nn.Module):
    def __init__(self, in_ch, out_ch):
        super().__init__()
        self.conv = nn.Conv2d(in_ch, out_ch, 1)
    def forward(self, x): return self.conv(x)

class UNet(nn.Module):
    def __init__(self, n_channels, n_classes, bilinear=False):
        super().__init__()
        factor = 2 if bilinear else 1
        self.inc   = DoubleConv(n_channels, 64)
        self.down1 = Down(64, 128)
        self.down2 = Down(128, 256)
        self.down3 = Down(256, 512)
        self.down4 = Down(512, 1024//factor)
        self.up1   = Up(1024, 512//factor, bilinear)
        self.up2   = Up(512, 256//factor, bilinear)
        self.up3   = Up(256, 128//factor, bilinear)
        self.up4   = Up(128, 64, bilinear)
        self.outc  = OutConv(64, n_classes)
    def forward(self, x):
        x1 = self.inc(x); x2 = self.down1(x1)
        x3 = self.down2(x2); x4 = self.down3(x3)
        x5 = self.down4(x4)
        x  = self.up1(x5, x4)
        x  = self.up2(x, x3)
        x  = self.up3(x, x2)
        x  = self.up4(x, x1)
        return self.outc(x)

MODEL_SEG = UNet(n_channels=3, n_classes=1, bilinear=False).to(DEVICE)
seg_state = torch.load("last_best_unet_f_001.pth", map_location=DEVICE)
seg_state = {k:v for k,v in seg_state.items() if k in MODEL_SEG.state_dict()}
MODEL_SEG.load_state_dict(seg_state, strict=False)
MODEL_SEG.eval()

# ───────────────────────────────────────────────────────────────
# 4) Firebase init
# ───────────────────────────────────────────────────────────────
cred = credentials.Certificate('thrombolaize-3f3a0-firebase-adminsdk-fbsvc-dc90099266.json')
firebase_admin.initialize_app(cred, {
    'storageBucket': 'thrombolaize-3f3a0.firebasestorage.app'
})
bucket = storage.bucket()

# ───────────────────────────────────────────────────────────────
# 5) Request & response schemas
# ───────────────────────────────────────────────────────────────
class PredictRequest(BaseModel):
    url: str

class PredictResponse(BaseModel):
    ischemic: str
    hemorrhagic: str
    normal: str
    predicted: str
    ischemickey: str = ""  # populated if ischemic

# ───────────────────────────────────────────────────────────────
# 6) Helpers: windowing, preprocessing
# ───────────────────────────────────────────────────────────────
def apply_windowing(dcm, center=40, width=80):
    intercept = getattr(dcm, 'RescaleIntercept', 0)
    slope     = getattr(dcm, 'RescaleSlope',     1)
    arr = dcm.pixel_array * slope + intercept
    lo, hi = center - width/2, center + width/2
    win = np.clip(arr, lo, hi)
    return ((win - lo)/(hi - lo)*255).astype(np.uint8)

def preprocess_cls(data: bytes, ext: str):
    prep = transforms.Compose([
        transforms.Resize((512,512)),
        transforms.ToTensor(),
    ])
    if ext == ".dcm":
        ds  = pydicom.dcmread(io.BytesIO(data))
        arr = apply_windowing(ds)
        arr = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8,8)).apply(arr)
        img = Image.fromarray(arr).convert('L')
    elif ext in (".jpg",".jpeg",".png"):
        img = Image.open(io.BytesIO(data)).convert('L')
#        arr = np.array(img)
#       arr = cv2.createCLAHE(clipLimit=2.0, tileGridSize=(8,8)).apply(arr)
#      img = Image.fromarray(arr)
    else:
        raise HTTPException(415, f"Unsupported ext {ext}")
    return prep(img).unsqueeze(0)

def load_image_bytes(data: bytes, ext: str):
    if ext == ".dcm":
        ds = pydicom.dcmread(io.BytesIO(data))
        arr = apply_windowing(ds)
        return Image.fromarray(arr).convert('L')
    else:
        return Image.open(io.BytesIO(data)).convert('L')

def preprocess_and_combine(pil_img: Image.Image):
    base = transforms.ToTensor()(pil_img).unsqueeze(0)
    base = F.interpolate(base, size=(512,512), mode='bilinear', align_corners=False)
    # Sobel
    kx = torch.tensor([[[[-1,0,1],[-2,0,2],[-1,0,1]]]], dtype=torch.float32)
    ky = torch.tensor([[[[-1,-2,-1],[0,0,0],[1,2,1]]]], dtype=torch.float32)
    gx = F.conv2d(base, kx, padding=1)
    gy = F.conv2d(base, ky, padding=1)
    sob = torch.sqrt(gx*gx + gy*gy).clamp(0,1)
    # LoG
    log_k = torch.tensor(
        [[[[0,0,-1,0,0],[0,-1,-2,-1,0],[-1,-2,16,-2,-1],[0,-1,-2,-1,0],[0,0,-1,0,0]]]]
    , dtype=torch.float32) / (2*np.pi)
    log = F.conv2d(base, log_k, padding=2).clamp(min=0)
    return torch.cat([base, sob, log], dim=1)

# ───────────────────────────────────────────────────────────────
# 7) Segmentation + upload
# ───────────────────────────────────────────────────────────────
def do_segmentation_and_upload(img_bytes: bytes, ext: str) -> str:
    orig = load_image_bytes(img_bytes, ext)
    inp  = preprocess_and_combine(orig).to(DEVICE)

    with torch.no_grad():
        logits = MODEL_SEG(inp)
        prob   = torch.sigmoid(logits)[0,0].cpu().numpy()
        mask   = (prob > 0.5).astype(np.uint8)  # 512×512

    orig_np = np.array(orig)
    H, W    = orig_np.shape
    mask_r  = cv2.resize(mask, (W,H), interpolation=cv2.INTER_NEAREST)

    # largest component
    n, labs = cv2.connectedComponents(mask_r)
    if n > 1:
        areas = [(labs==i).sum() for i in range(1,n)]
        best  = np.argmax(areas) + 1
        mf    = (labs==best).astype(np.uint8)
    else:
        mf = mask_r

    # flip & erode
    mf = np.fliplr(mf)
    mf = cv2.erode(mf, np.ones((5,5), np.uint8), iterations=1)

    # overlay
    color = np.stack([orig_np]*3, axis=2).astype(np.float32)
    alpha = 0.3
    red   = np.array([255,0,0], dtype=np.float32)
    idx   = mf == 1
    color[idx] = alpha*red + (1-alpha)*color[idx]
    pil = Image.fromarray(color.clip(0,255).astype(np.uint8))

    # upload
    buf  = io.BytesIO()
    pil.save(buf, format='PNG')
    buf.seek(0)
    name = f"{uuid.uuid4().hex}_seg.png"
    blob = bucket.blob(f"segmentation-response/{name}")
    blob.upload_from_file(buf, content_type='image/png')
    blob.make_public()
    return blob.public_url

# ───────────────────────────────────────────────────────────────
# 8) /predict endpoint
# ───────────────────────────────────────────────────────────────
@app.post("/predict", response_model=PredictResponse)
def predict(req: PredictRequest):
    resp = requests.get(req.url)
    if resp.status_code != 200:
        raise HTTPException(400, "Could not fetch image")

    fname = os.path.basename(unquote(urlparse(req.url).path))
    _, ext = os.path.splitext(fname.lower())

    # classification
    tensor = preprocess_cls(resp.content, ext).to(DEVICE)
    with torch.no_grad():
        logits = MODEL_CLS(tensor)
        probs  = torch.softmax(logits, dim=1).cpu().numpy().squeeze()
    isc_p = float(probs[0] + probs[1])
    hem_p = float(probs[2])
    nor_p = float(probs[3])
    labels = ["Ischemic","Hemorrhagic","Normal"]
    pred   = labels[int(np.argmax([isc_p, hem_p, nor_p]))]

    res = PredictResponse(
        ischemic    = f"{isc_p*100:.2f}%",
        hemorrhagic = f"{hem_p*100:.2f}%",
        normal      = f"{nor_p*100:.2f}%",
        predicted   = pred,
    )

    if pred == "Ischemic":
        res.ischemickey = do_segmentation_and_upload(resp.content, ext)

    return res