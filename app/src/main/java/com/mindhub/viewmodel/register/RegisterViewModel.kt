package com.mindhub.viewmodel.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.RegisterRequest
import com.mindhub.model.api.UserFakeApi
import com.mindhub.model.entities.Expertise
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordConfirmation by mutableStateOf("")
    var feedback by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun register(
        selectedExpertises: List<Expertise>,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                isLoading = true
                val res = UserFakeApi.register(RegisterRequest(name, email, username, password, selectedExpertises))
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }

            isLoading = false
        }
    }
}