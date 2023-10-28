package com.mindhub.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.common.services.ErrorParser
import com.mindhub.model.api.EventFakeApi
import com.mindhub.model.entities.Event
import kotlinx.coroutines.launch

class GetEventViewModel : ViewModel() {
    var event: Event? by mutableStateOf(null)
    var feedback by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun get(id: Int) {
        viewModelScope.launch {
            feedback = ""
            isLoading = true

            try {
                event = EventFakeApi.get(id)
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)
            }

            isLoading = false
        }
    }
}