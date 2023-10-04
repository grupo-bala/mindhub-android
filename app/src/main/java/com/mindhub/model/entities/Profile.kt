package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val name: String,
    val username: String,
    val email: String,
    val achievement: Badge,
)