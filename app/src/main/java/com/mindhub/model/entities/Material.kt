package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Material(
    var id: Int,
    var user: User,
    var title: String,
    var content: String,
    var expertise: Expertise
)
