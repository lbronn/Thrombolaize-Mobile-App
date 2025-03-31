package com.example.thrombolaize.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.thrombolaize.R
import com.example.thrombolaize.helperclasses.HospitalsList
import com.example.thrombolaize.helperclasses.hospitalMarkerIcon
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Hospitals() {
    val context = LocalContext.current
    val hospitalsList = HospitalsList.hospitalList
    var isMapsInitialized by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        MapsInitializer.initialize(context.applicationContext, MapsInitializer.Renderer.LATEST) {
            isMapsInitialized = true
        }
    }

    if (!isMapsInitialized) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val markerIcon = hospitalMarkerIcon(
        R.drawable.hospital_marker_vector
    )
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(hospitalsList.first().latLng, 14f)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            hospitalsList.forEach { hospital ->
                val markerState = remember {
                    MarkerState(position = hospital.latLng)
                }
                Marker(
                    state = markerState,
                    title = hospital.title,
                    icon = markerIcon
                )
            }
        }
    }
}