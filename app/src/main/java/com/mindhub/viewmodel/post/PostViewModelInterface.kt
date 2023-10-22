package com.mindhub.viewmodel.post

import com.mindhub.model.entities.Expertise

interface PostViewModelInterface {
    var title: String
    var content: String
    var expertise: Expertise

    fun create(
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )
    fun update(
        postId: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
    )
    fun remove(
        postId: Int,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    )
    fun getType(): String
}