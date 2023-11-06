package com.mindhub.viewmodel.ask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.AskFakeApi
import com.mindhub.model.entities.Post
import com.mindhub.viewmodel.post.FeedPostViewModel
import kotlinx.coroutines.launch

class FeedAskViewModel : ViewModel(), FeedPostViewModel {
    override var forYou: MutableList<Post> = mutableStateListOf<Post>()
    override var recents: MutableList<Post> = mutableStateListOf<Post>()
    override var currentPageForYou by mutableIntStateOf(1)
    override var currentPageRecents by mutableIntStateOf(1)
    override var isLoadingForYou by mutableStateOf(false)
    override var isLoadingRecents by mutableStateOf(false)

    init {
        this.getForYou()
        this.getRecents()
    }

    override fun getForYou() {
        viewModelScope.launch {
            try {
                isLoadingForYou = true
                forYou.addAll(AskFakeApi.getForYou(currentPageForYou))
                currentPageForYou++
            } catch (_: Exception) { }

            isLoadingForYou = false
        }
    }

    override fun getRecents() {
        viewModelScope.launch {
            try {
                isLoadingRecents = true
                recents.addAll(AskFakeApi.getRecents(currentPageRecents))
                currentPageRecents++
            } catch (_: Exception) { }

            isLoadingRecents = false
        }
    }
}