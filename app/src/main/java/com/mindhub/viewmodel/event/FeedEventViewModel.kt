package com.mindhub.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.EventFakeApi
import com.mindhub.model.entities.Event
import kotlinx.coroutines.launch

class FeedEventViewModel : ViewModel() {
    var eventsForYou = mutableStateListOf<Event>()
    var eventsRecent = mutableStateListOf<Event>()
    var currentPageForYou by mutableIntStateOf(1)
    var currentPageRecents by mutableIntStateOf(1)
    var isLoadingForYou by mutableStateOf(false)
    var isLoadingRecents by mutableStateOf(false)

    fun getForYou() {
        viewModelScope.launch {
            try {
                isLoadingForYou = true
                eventsForYou.addAll(EventFakeApi.getForYou(currentPageForYou))
                currentPageForYou++
            } catch (_: Exception) { }

            isLoadingForYou = false
        }
    }

    fun getRecents() {
        viewModelScope.launch {
            try {
                isLoadingRecents = true
                eventsRecent.addAll(EventFakeApi.getRecents(currentPageRecents))
                currentPageRecents++
            } catch (_: Exception) { }

            isLoadingRecents = false
        }
    }
}