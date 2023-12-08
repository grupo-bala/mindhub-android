package com.mindhub.viewmodel.comment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class HandleCommentCreationViewModel : ViewModel() {
    var commentText by mutableStateOf("")

    fun clear() {
        commentText = ""
    }
}