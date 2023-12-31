package com.mindhub.viewmodel.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mindhub.model.api.RegisterRequest
import com.mindhub.model.api.UserApi
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
        if (!validate()) {
            return
        }

        viewModelScope.launch {
            try {
                isLoading = true
                UserApi.register(RegisterRequest(name, email, username, password, selectedExpertises.map { it.title }))
                onSuccess()
            } catch (e: Exception) {
                onFailure(e.message)
            }

            isLoading = false
        }
    }

    fun isFilled(): Boolean {
        return name != "" && username != "" && email != "" && password != "" && passwordConfirmation != ""
    }
    fun validate(): Boolean {
        if (!isFilled()) {
            feedback = "Preenha todos os campos"
            return false
        }

        if (password != passwordConfirmation) {
            feedback = "As senhas não coincidem"
            return false
        }

        return true
    }
}