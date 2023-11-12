package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Int,
    val postId: Int,
    val username: String,
    var content: String,
    var isBestAnswer: Boolean,
    var score: Int,
    var userScore: Int,
    var replies: MutableList<Comment>,
)
