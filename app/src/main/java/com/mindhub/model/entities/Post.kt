package com.mindhub.model.entities

import java.time.LocalDateTime

interface Post {
    var id: Int
    var user: User
    var title: String
    var content: String
    var score: Int
    var date: LocalDateTime
}