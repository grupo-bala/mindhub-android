package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Material(
    override var id: Int,
    override var user: User,
    override var title: String,
    override var content: String,
    override var score: Int,
    var expertise: Expertise
) : Post
