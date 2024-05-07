package com.example.shoppingl_list_app.ui.theme.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import java.lang.reflect.Modifier

@Composable
fun MapScreen() {
    val userLocation = LatLng(40.7128, -74.0060) // Example location, replace with actual user location
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation, 15f)
    }
    GoogleMap(
       modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    )
}
