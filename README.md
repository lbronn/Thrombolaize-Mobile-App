<h1>Thrombolaize: Your Rapid Stroke Care Triage Tool</h1>

<p>Thrombolaize is a <b>Jetpack-Compose Android application</b> that helps stroke teams triage patients by instantly analyzing uploaded CT scans with an on-device AI model. Alongside imaging, clinicians enter age, sex, onset window and arrival time, and the app returns an evidence-based IV-tPA recommendation while logging every decision to Firebase for audit. End-to-end encryption plus Google Cloud-KMS ensure patient privacy, and a local cache keeps the workflow alive offline.</p>

<p>The codebase uses Hilt, Room, DataStore and reactive StateFlow. Suggestions and constructive criticisms are welcome; open an issue or PR to join the mission of accelerating stroke care everywhere.</p>

<h2>🧠 Key Features of Thrombolaize:</h2>
<ul>
  <li>
    <b>Instant CT Analysis</b> – Upload a brain-imaging file and get an on-device ML inference (ischemic vs. hemorrhagic likelihood and IV-tPA eligibility) in seconds, even offline.
  </li>
  <li>
    <b>Structured Stroke Workflow</b> – Guided forms capture age, sex, onset-to-door time and vitals, then generate a standardized treatment summary with one-tap export to EMR or PDF.
  </li>
  <li>
    <b>Secure Multi-Device Sync</b> – Patient cases, images and audit logs are end-to-end encrypted, stored in Firebase + Cloud KMS, and seamlessly synchronized across the care team’s phones and tablets.
  </li>
  <li>
    <b>Timeline & History Dashboard</b> – A sortable timeline shows every “Thrombolaized” patient with date, time and outcome; tapping a row opens the full case for review or follow-up.
  </li>
  <li>
    <b>Modular, Open Architecture</b> – Built with Jetpack Compose, Hilt, Room and StateFlow; contributors can plug in new ML models, add languages, or swap back-ends via clean repository patterns.
  </li>
</ul>
