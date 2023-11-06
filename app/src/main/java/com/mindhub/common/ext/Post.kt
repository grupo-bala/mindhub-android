package com.mindhub.common.ext

import com.mindhub.model.entities.Ask
import com.mindhub.model.entities.Material
import com.mindhub.model.entities.Post
import io.ktor.util.reflect.instanceOf

fun Post.getRoute(): String {
    val postType = if (this.instanceOf(Ask::class)) {
        "ask"
    } else if (this.instanceOf(Material::class)) {
        "material"
    } else {
        "event"
    }

    return "https://mindhub.netlify.app/$postType/${this.id}"
}