package com.mindhub.viewmodel.profile

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.common.services.CurrentUser
import com.mindhub.common.services.ErrorParser
import com.mindhub.model.api.UpdateInfoRequest
import com.mindhub.model.api.UserApi
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class EditProfileViewModel() : ViewModel() {
    var name by mutableStateOf(CurrentUser.user!!.name)
    var email by mutableStateOf(CurrentUser.user!!.email)
    var password by mutableStateOf("")
    var photo by mutableStateOf<Bitmap?>(null)
    var feedback by mutableStateOf("")

    fun update(
        selectedBadge: Badge,
        selectedExpertises: List<Expertise>,
        onSuccess: () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                UserApi.update(
                    UpdateInfoRequest(
                        name,
                        email,
                        password,
                        selectedExpertises
                    ),
                    bitMapToByteArray(photo)
                )

                onSuccess()
            } catch (e: Exception) {
                feedback = ErrorParser.from(e.message)
            }
        }
    }

    private fun bitMapToByteArray(bitmap: Bitmap?): ByteArray? {
        return if (bitmap == null) {
            null
        } else {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
            stream.toByteArray()
        }
    }
}
