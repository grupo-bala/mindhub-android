package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var name: String,
    var email: String,
    var username: String,
    var xp: Int,
    var currentBadge: Int,
    var expertises: List<Expertise>,
    var token: String
)
