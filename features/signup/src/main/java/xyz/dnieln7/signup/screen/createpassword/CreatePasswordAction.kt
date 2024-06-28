package xyz.dnieln7.signup.screen.createpassword

sealed interface CreatePasswordAction {
    data class OnPasswordInput(val text: String) : CreatePasswordAction

    data class OnPasswordConfirmInput(val text: String) : CreatePasswordAction

    data class OnCreatePasswordClick(
        val email: String,
        val password: String,
        val username: String,
    ) : CreatePasswordAction
}
