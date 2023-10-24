package com.mindhub.viewmodel.ask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.AskFakeApi
import com.mindhub.model.entities.Ask
import kotlinx.coroutines.launch

class FeedAskViewModel : ViewModel() {
    var asksForYou = mutableStateListOf<Ask>()
    var asksRecent = mutableStateListOf<Ask>()
    var currentPageForYou by mutableIntStateOf(1)
    var currentPageRecents by mutableIntStateOf(1)
    var isLoadingForYou by mutableStateOf(false)
    var isLoadingRecents by mutableStateOf(false)

    fun getForYou() {
        viewModelScope.launch {
            try {
                isLoadingForYou = true
                asksForYou.addAll(AskFakeApi.getForYou(currentPageForYou))
                currentPageForYou++
            } catch (_: Exception) { }

            isLoadingForYou = false
        }
    }

    fun getRecents() {
        viewModelScope.launch {
            try {
                isLoadingRecents = true
                asksRecent.addAll(AskFakeApi.getRecents(currentPageRecents))
                currentPageRecents++
            } catch (_: Exception) { }

            isLoadingRecents = false
        }
    }
}