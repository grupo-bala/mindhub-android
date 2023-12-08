package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: Int,
    var content: String,
    val post: Int,
    val user: String,
    var isBestAnswer: Boolean,
    var replies: MutableList<Comment>,
    var userScore: Int,
    var score: Int,
    var replyTo: Int?,
)
