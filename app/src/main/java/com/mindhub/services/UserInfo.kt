package com.mindhub.services

import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise

object UserInfo {
    var name: String = ""
    var username: String = ""
    var email: String = ""
    var profilePictureURL: String = ""
    var xp: Int? = 0
    var achievement: Badge? = null
    var expertises: List<Expertise>? = null
}
