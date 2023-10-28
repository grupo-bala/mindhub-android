package com.mindhub.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.mindhub.common.ext.dateToUnix
import com.mindhub.common.services.UserInfo
import com.mindhub.model.api.EventFakeApi
import com.mindhub.model.entities.Event
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class EventViewModel : ViewModel() {
    var title by mutableStateOf("")
    var content by mutableStateOf("")
    var date by mutableStateOf("")
    var time by mutableStateOf("")
    var position by mutableStateOf(LatLng(0.0, 0.0))
    var isLoading by mutableStateOf(false)

    fun create(
        onSuccess: (Event) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true

                val event = EventFakeApi.create(
                    Event(
                        id = 0,
                        user = UserInfo!!,
                        title = title,
                        content = content,
                        score = 0,
                        postDate = LocalDateTime.now(),
                        date = date.dateToUnix(time),
                        latitude = position.latitude,
                        longitude = position.longitude
                    )
                )

                onSuccess(event)
            } catch (e: Error) {

            }

            isLoading = false
        }
    }
}