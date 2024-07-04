package xyz.dnieln7.signup.screen.createuser

sealed interface CreateUserAction {
    data class OnEmailInput(val text: String) : CreateUserAction
    data class OnUsernameInput(val text: String) : CreateUserAction
    data class OnCreateUserClick(val email: String, val username: String) : CreateUserAction
    data class OnUserCreated(val email: String, val username: String) : CreateUserAction
}
