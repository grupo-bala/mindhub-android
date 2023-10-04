package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val email: String,
    val username: String,
    val xp: Int,
    val currentBadge: Int,
    val expertises: List<Expertise>,
    val token: String
)
