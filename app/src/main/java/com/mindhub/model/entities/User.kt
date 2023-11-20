package com.mindhub.model.entities

import android.net.Uri
import com.mindhub.common.serialize.UriSerializer
import kotlinx.serialization.Serializable

@Serializable
data class User(
    var name: String,
    var email: String,
    var username: String,
    var xp: Int,
    var currentBadge: Badge,
    var expertises: List<Expertise>,
    var token: String,
    @Serializable(UriSerializer::class)
    var profilePicture: Uri? = null,
)
