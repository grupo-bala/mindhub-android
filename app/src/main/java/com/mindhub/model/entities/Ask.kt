package com.mindhub.model.entities

import android.net.Uri
import com.mindhub.common.serialize.DateSerializer
import com.mindhub.common.serialize.UriSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Ask(
    override var id: Int,
    override var title: String,
    override var userScore: Int,
    override var content: String,
    override var score: Int,
    override var user: User,
    @Serializable(DateSerializer::class)
    override var postDate: LocalDateTime,
    var expertise: Expertise,
    @Serializable(UriSerializer::class)
    var file: Uri? = null
) : Post