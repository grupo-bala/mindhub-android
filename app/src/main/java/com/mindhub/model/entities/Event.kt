package com.mindhub.model.entities

import com.mindhub.common.serialize.DateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Event(
    override var id: Int,
    override var user: User,
    override var title: String,
    override var userScore: Int,
    override var content: String,
    override var score: Int,
    @Serializable(DateSerializer::class)
    override var postDate: LocalDateTime,
    @Serializable(DateSerializer::class)
    var date: LocalDateTime,
    var latitude: Double,
    var longitude: Double,
    var localName: String,
) : Post
