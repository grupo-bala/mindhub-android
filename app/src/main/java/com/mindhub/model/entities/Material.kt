package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Material(
    var user: String,
    var title: String,
    var content: String,
    var expertise: List<Expertise>
)
