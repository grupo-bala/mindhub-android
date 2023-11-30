package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class LeaderboardEntry(
    val username: String,
    val name: String,
    val xp: Int
)