package com.mindhub.viewmodel.material

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.MaterialFakeApi
import com.mindhub.model.entities.Material
import kotlinx.coroutines.launch

class FeedMaterialViewModel : ViewModel() {
    var materialsForYou = mutableStateListOf<Material>()
    var materialsRecents = mutableStateListOf<Material>()
    var currentPageForYou by mutableIntStateOf(1)
    var currentPageRecents by mutableIntStateOf(1)
    var isLoadingForYou by mutableStateOf(false)
    var isLoadingRecents by mutableStateOf(false)

    fun getForYou() {
        viewModelScope.launch {
            try {
                isLoadingForYou = true
                materialsForYou.addAll(MaterialFakeApi.getForYou(currentPageForYou))
                currentPageForYou++
            } catch (_: Exception) { }

            isLoadingForYou = false
        }
    }

    fun getRecents() {
        viewModelScope.launch {
            try {
                isLoadingRecents = true
                materialsRecents.addAll(MaterialFakeApi.getRecents(currentPageRecents))
                currentPageRecents++
            } catch (_: Exception) { }

            isLoadingRecents = false
        }
    }
}