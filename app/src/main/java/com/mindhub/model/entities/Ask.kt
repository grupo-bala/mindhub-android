package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Ask(
    val id: Int,
    val title: String,
    val content: String,
    val expertise: Expertise,
    val score: Int,
    val user: User
)
