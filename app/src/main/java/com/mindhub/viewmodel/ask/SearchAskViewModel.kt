package com.mindhub.viewmodel.ask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.AskApi
import com.mindhub.model.entities.Ask
import kotlinx.coroutines.launch

class SearchAskViewModel : ViewModel() {
    var asks = mutableStateListOf<Ask>()
    var inputTitle by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var isFirstSearch by mutableStateOf(true)

    fun get(
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                asks.clear()
                asks.addAll(AskApi.get(inputTitle))
            } catch (e: Exception) {
                onFailure(e.message)
            }

            isLoading = false
        }
    }
}