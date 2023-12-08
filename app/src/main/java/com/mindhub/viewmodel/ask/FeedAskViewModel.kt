package com.mindhub.viewmodel.ask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.AskApi
import com.mindhub.model.entities.Post
import com.mindhub.viewmodel.post.FeedPostViewModel
import kotlinx.coroutines.launch

class FeedAskViewModel : ViewModel(), FeedPostViewModel {
    override var forYou: MutableList<Post> = mutableStateListOf<Post>()
    override var recents: MutableList<Post> = mutableStateListOf<Post>()
    override var isLoadingForYou by mutableStateOf(false)
    override var isLoadingRecents by mutableStateOf(false)

    override fun getForYou() {
        viewModelScope.launch {
            try {
                isLoadingForYou = true
                forYou.clear()
                forYou.addAll(AskApi.getForYou())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            isLoadingForYou = false
        }
    }

    override fun getRecents() {
        viewModelScope.launch {
            try {
                isLoadingRecents = true
                recents.clear()
                recents.addAll(AskApi.getRecents())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            isLoadingRecents = false
        }
    }
}