package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Badge(val title: String, val id: Int, val xp: Int)