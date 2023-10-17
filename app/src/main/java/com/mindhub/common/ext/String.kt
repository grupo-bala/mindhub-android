package com.mindhub.common.ext

fun String.ellipsis(maxSize: Int): String {
    if (maxSize >= this.length) {
        return this
    }

    return "${this.substring(0, maxSize - 1)}..."
}