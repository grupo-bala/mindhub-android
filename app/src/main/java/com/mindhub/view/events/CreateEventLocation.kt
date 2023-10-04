package com.mindhub.view.events

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Destination
@Composable
fun CreateEventLocation(
    navigator: DestinationsNavigator,
    title: String,
    content: String,
    timestamp: String,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            TopAppBar(
                title = { Text(text = "Adicionar um evento", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                    }
                },
                actions = {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Adicionar")
                    }
                }
            )

            SpacedColumn(
                spacing = 8,
                verticalAlignment = Alignment.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1.0f)
            ) {
                val locationPermissionState = rememberPermissionState(
                    permission = android.Manifest.permission.ACCESS_FINE_LOCATION
                )

                LaunchedEffect(key1 = true) {
                    locationPermissionState.launchPermissionRequest()
                }

                var eventPosition by remember { mutableStateOf(LatLng(0.0, 0.0)) }

                if (locationPermissionState.status.isGranted) {
                    val context = LocalContext.current
                    val locationService = LocationServices.getFusedLocationProviderClient(context)
                    val cameraPositionState = rememberCameraPositionState {
                        position = CameraPosition.fromLatLngZoom(LatLng(0.0, 0.0), 10.0f)
                    }

                    LaunchedEffect(key1 = true) {
                        locationService.lastLocation.addOnSuccessListener {
                            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                                LatLng(it.latitude, it.longitude),
                                15.0f
                            )
                        }
                    }

                    GoogleMap(
                        cameraPositionState = cameraPositionState,
                        onMapClick = { eventPosition = it },
                        properties = MapProperties(isMyLocationEnabled = true),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.0f)
                    ) {
                        Marker(
                            state = MarkerState(eventPosition)
                        )
                    }
                } else {
                    GoogleMap(
                        onMapClick = { eventPosition = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.0f)
                    ) {
                        Marker(
                            state = MarkerState(eventPosition)
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun CreateEventLocationPreview() {
    MindHubTheme {
        CreateEventLocation(navigator = EmptyDestinationsNavigator, "", "", "")
    }
}