package com.mindhub.model.entities

interface Post {
    var id: Int
    var user: User
    var title: String
    var content: String
    var score: Int
}