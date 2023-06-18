package com.example.countries.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapView(
    latLng: LatLng,
    title: String,
    desc: String,
    modifier: Modifier = Modifier
){
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 3f)
    }

    LaunchedEffect(cameraPositionState.isMoving){
        Log.e("Maps", "Map recomposing")
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        Marker(
            state = MarkerState(position = latLng),
            title = title,
            snippet = desc
        )
    }
}