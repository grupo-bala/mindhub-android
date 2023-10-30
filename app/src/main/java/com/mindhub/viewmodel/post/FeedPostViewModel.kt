package com.mindhub.viewmodel.post

import com.mindhub.model.entities.Post

interface FeedPostViewModel {
    var forYou: MutableList<Post>
    var recents: MutableList<Post>
    var currentPageForYou: Int
    var currentPageRecents: Int
    var isLoadingForYou: Boolean
    var isLoadingRecents: Boolean

    fun getForYou()
    fun getRecents()
}