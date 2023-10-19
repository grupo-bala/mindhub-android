package com.mindhub.model.entities

import kotlinx.serialization.Serializable

@Serializable
data class Ask(
    override var id: Int,
    override var title: String,
    override var content: String,
    override var score: Int,
    override var user: User,
    var expertise: Expertise
) : Post
