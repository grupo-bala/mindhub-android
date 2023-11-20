package com.mindhub.viewmodel.profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.UpdateRequest
import com.mindhub.model.api.UserFakeApi
import com.mindhub.model.entities.Badge
import com.mindhub.model.entities.Expertise
import com.mindhub.common.services.UserInfo
import kotlinx.coroutines.launch

class EditProfileViewModel() : ViewModel() {
    var name by mutableStateOf(UserInfo!!.name)
    var email by mutableStateOf(UserInfo!!.email)
    var photo by mutableStateOf<Uri?>(null)
    var isLoading by mutableStateOf(false)

    fun update(
        selectedBadge: Badge,
        selectedExpertises: List<Expertise>,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true

                UserFakeApi.update(UpdateRequest(name, email, selectedExpertises, selectedBadge, photo))

                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }

            isLoading = false
        }
    }
}
