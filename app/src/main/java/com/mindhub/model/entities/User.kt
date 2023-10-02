package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val email: String,
    val username: String,
)
