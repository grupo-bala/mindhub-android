package com.mindhub.view.composables.events

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.layouts.SpacedColumn

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EventLocationInput(
    currentPosition: LatLng,
    onChange: (LatLng) -> Unit
) {
    SpacedColumn(
        spacing = 8,
        verticalAlignment = Alignment.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.height(400.dp)
    ) {
        val locationPermissionState = rememberPermissionState(
            permission = Manifest.permission.ACCESS_FINE_LOCATION
        )

        LaunchedEffect(key1 = true) {
            locationPermissionState.launchPermissionRequest()
        }

        if (locationPermissionState.status.isGranted) {
            val context = LocalContext.current
            val locationService = LocationServices.getFusedLocationProviderClient(context)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 10.0f)
            }

            LaunchedEffect(key1 = true) {
                locationService.lastLocation.addOnSuccessListener {
                    if (it != null) {
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(
                            LatLng(it.latitude, it.longitude),
                            15.0f
                        )
                    }
                }
            }

            GoogleMap(
                cameraPositionState = cameraPositionState,
                onMapClick = { onChange(it) },
                properties = MapProperties(isMyLocationEnabled = true),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
            ) {
                Marker(
                    state = MarkerState(currentPosition)
                )
            }
        } else {
            GoogleMap(
                onMapClick = { onChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
            ) {
                Marker(
                    state = MarkerState(currentPosition)
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CreateEventLocationPreview() {
    MindHubTheme {
        EventLocationInput(
            currentPosition = LatLng(0.0, 0.0),
            onChange = {}
        )
    }
}