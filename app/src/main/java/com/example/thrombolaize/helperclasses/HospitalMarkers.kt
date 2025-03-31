package com.example.thrombolaize.helperclasses

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng

data class HospitalMarkers(
    val lat: Double,
    val lng: Double,
    val title: String,
    val icon: BitmapDescriptor? = null
) {
    val latLng: LatLng get() = LatLng(lat, lng)
}