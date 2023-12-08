package com.mindhub.common.services

object ErrorParser {
    fun from(error: String?): String {
        return when (error) {
            "WRONG CREDENTIALS" -> "Email ou senha inválidos"
            "DUPLICATE EMAIL" -> "Esse email já está em uso"
            "DUPLICATE USERNAME" -> "Esse nome de usuário já está em uso"
            "USER NEED AT LEAST ONE EXPERTISE" -> "Selecione pelo menos 1 área de especialidade"
            "USER CAN HAVE UNTIL 3 EXPERTISES" -> "Você pode ter no máximo 3 áreas de especialidade"
            "POST DOESNT EXIST" -> "O post não existe"
            "COMMENT DOESNT EXIST" -> "O comentário não existe"
            "COMMENT TO REPLY OR POST DOESNT EXIST" -> "O comentário ou o post não existe"
            else -> "Ops... Houve um erro inesperado"
        }
    }
}