package com.mindhub.view.composables.events

import android.Manifest
import android.annotation.SuppressLint
import android.widget.Button
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
    currentPosition: LatLng?,
    onConfirm: () -> Unit,
    onChange: (LatLng) -> Unit
) {
    Box {
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
                    .fillMaxSize()
            ) {
                if (currentPosition != null) {
                    Marker(
                        state = MarkerState(currentPosition)
                    )
                }
            }
        } else {
            GoogleMap(
                onMapClick = { onChange(it) },
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if (currentPosition != null) {
                    Marker(
                        state = MarkerState(currentPosition)
                    )
                }
            }
        }

        if (currentPosition != null) {
            Button(
                onClick = { onConfirm() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp)
                    .width(256.dp)
            ) {
                Text(text = "Confirmar", color = MaterialTheme.colorScheme.onPrimary)
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
            onConfirm = {},
            onChange = {}
        )
    }
}