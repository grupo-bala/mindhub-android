package com.mindhub.viewmodel.ask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.AskApi
import com.mindhub.model.entities.Ask
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchAskViewModel : ViewModel() {
    var asks = mutableStateListOf<Ask>()
    var isLoading by mutableStateOf(false)
    var isFirstSearch by mutableStateOf(true)

    private val _input = MutableStateFlow("")
    val inputText: StateFlow<String> = _input

    fun updateInput(input: String) {
        _input.update { input }
    }

    init {
        viewModelScope.launch {
            try {
                inputText.debounce(500).collectLatest {
                    if (it.isNotEmpty()) {
                        isLoading = true
                        isFirstSearch = false

                        val result = AskApi.get(it)

                        println(it)
                        println(result)

                        asks.clear()
                        asks.addAll(result)

                        println(asks)
                        isLoading = false
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}