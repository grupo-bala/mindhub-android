package com.mindhub.viewmodel.post

import com.mindhub.model.entities.Post

interface GetPostViewModel {
    var post: Post?
    var feedback: String
    var isLoading: Boolean

    fun get(id: Int)
}