package com.mindhub.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.maps.GeoApiContext
import com.google.maps.GeocodingApi
import com.google.maps.PendingResult
import com.google.maps.model.GeocodingResult
import com.mindhub.BuildConfig
import com.mindhub.common.ext.dateToUnix
import com.mindhub.common.services.UserInfo
import com.mindhub.model.api.EventApi
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
    override var feedback: String? by mutableStateOf("")
    var date by mutableStateOf("")
    var time by mutableStateOf("")
    var position by mutableStateOf<LatLng?>(null)
    var positionName by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var isPositionNameLoading by mutableStateOf(false)

    override fun create(
        onSuccess: (Post) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true

                isValid()

                val event = EventApi.create(
                    Event(
                        id = 0,
                        user = UserInfo!!,
                        title = title,
                        content = content,
                        score = 0,
                        postDate = LocalDateTime.now(),
                        date = date.dateToUnix(time),
                        latitude = position!!.latitude,
                        longitude = position!!.longitude,
                        userScore = 0,
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
        isValid()

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
                        latitude = position!!.latitude,
                        longitude = position!!.longitude,
                        userScore = 0,
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

    fun loadPositionName() {
        isPositionNameLoading = true
        positionName = "Carregando..."

        val context = GeoApiContext.Builder()
            .apiKey(BuildConfig.apiKey)
            .build()

        GeocodingApi.reverseGeocode(
            context,
            com.google.maps.model.LatLng(position!!.latitude, position!!.longitude)
        ).setCallback(object : PendingResult.Callback<Array<GeocodingResult?>?> {
            override fun onResult(result: Array<GeocodingResult?>?) {
                positionName = result!![0]!!.formattedAddress
            }

            override fun onFailure(e: Throwable) {
                positionName = "Sem endereço"
            }
        })

        isPositionNameLoading = false
    }

    override fun isValid(): Boolean {
        if (!isFilled()) {
            this.feedback = "Preencha todos campos obrigatórios"
            return false
        }
        return true
    }

    override fun isFilled(): Boolean {
        return this.title != "" && this.content != "" && this.time != "" && this.date != "" && this.position != null
    }
}