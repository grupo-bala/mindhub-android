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
    var badges: List<Badge>,
    var expertises: List<Expertise>,
    @Serializable(UriSerializer::class)
    var profilePicture: Uri? = null,
)
