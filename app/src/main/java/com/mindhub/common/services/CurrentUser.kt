package com.mindhub.common.services

import com.mindhub.model.entities.User
import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    var user: User?,
    var token: String
)

var CurrentUser = UserInfo(null, "")