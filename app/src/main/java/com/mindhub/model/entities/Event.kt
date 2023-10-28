package com.mindhub.model.entities

import java.time.LocalDateTime

data class Event(
    override var id: Int,
    override var user: User,
    override var title: String,
    override var content: String,
    override var score: Int,
    override var postDate: LocalDateTime,
    var date: LocalDateTime,
    var latitude: Double,
    var longitude: Double
) : Post
