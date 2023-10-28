package com.mindhub.model.entities

import com.mindhub.common.serialize.DateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Ask(
    override var id: Int,
    override var title: String,
    override var content: String,
    override var score: Int,
    override var user: User,
    @Serializable(DateSerializer::class)
    override var postDate: LocalDateTime,
    var expertise: Expertise,
) : Post