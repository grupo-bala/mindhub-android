package com.mindhub.model.entities

import com.mindhub.common.serialize.DateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Material(
    override var id: Int,
    override var user: User,
    override var title: String,
    override var userScore: Int,
    override var content: String,
    override var score: Int,
    @Serializable(DateSerializer::class)
    override var postDate: LocalDateTime,
    var expertise: Expertise
) : Post
