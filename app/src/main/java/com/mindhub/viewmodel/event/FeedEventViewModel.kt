package com.mindhub.viewmodel.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.EventFakeApi
import com.mindhub.model.entities.Post
import com.mindhub.viewmodel.post.FeedPostViewModel
import kotlinx.coroutines.launch

class FeedEventViewModel : ViewModel(), FeedPostViewModel {
    override var forYou: MutableList<Post> = mutableStateListOf<Post>()
    override var recents: MutableList<Post> = mutableStateListOf<Post>()
    override var isLoadingForYou by mutableStateOf(false)
    override var isLoadingRecents by mutableStateOf(false)

    override fun getForYou() {
        viewModelScope.launch {
            try {
                isLoadingForYou = true
                forYou.clear()
                forYou.addAll(EventFakeApi.getForYou(1))
            } catch (_: Exception) { }

            isLoadingForYou = false
        }
    }

    override fun getRecents() {
        viewModelScope.launch {
            try {
                isLoadingRecents = true
                recents.clear()
                recents.addAll(EventFakeApi.getRecents(1))
            } catch (_: Exception) { }

            isLoadingRecents = false
        }
    }
}