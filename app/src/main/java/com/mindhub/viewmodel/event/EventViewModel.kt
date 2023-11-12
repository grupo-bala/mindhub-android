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
import com.mindhub.model.entities.Expertise
import com.mindhub.model.entities.Post
import com.mindhub.viewmodel.post.PostViewModelInterface
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class EventViewModel : ViewModel(), PostViewModelInterface {
    override var title by mutableStateOf("")
    override var content by mutableStateOf("")
    override var expertise: Expertise? = null
    var date by mutableStateOf("")
    var time by mutableStateOf("")
    var position by mutableStateOf(LatLng(0.0, 0.0))
    var isLoading by mutableStateOf(false)

    override fun create(
        onSuccess: (Post) -> Unit,
        onFailure: (String?) -> Unit
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
                        longitude = position.longitude,
                        localName = ""
                    )
                )

                onSuccess(event)
            } catch (e: Error) {
                onFailure(e.message)
            }

            isLoading = false
        }
    }

    override fun update(
        postId: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                isLoading = true

                EventFakeApi.update(
                    Event(
                        id = postId,
                        user = UserInfo!!,
                        title = title,
                        content = content,
                        postDate = LocalDateTime.now(),
                        score = 0,
                        date = date.dateToUnix(time),
                        latitude = position.latitude,
                        longitude = position.longitude,
                        localName = ""
                    )
                )

                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }

            isLoading = false
        }
    }

    override fun remove(
        postId: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        viewModelScope.launch {
            try {
                isLoading = true

                EventFakeApi.remove(postId)

                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }

            isLoading = false
        }
    }

    override fun getType(): String {
        return "evento"
    }
}