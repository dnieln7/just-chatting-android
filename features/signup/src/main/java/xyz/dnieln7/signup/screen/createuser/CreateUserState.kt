package xyz.dnieln7.signup.screen.createuser

import xyz.dnieln7.composable.button.StatefulButtonStatus

sealed class CreateUserState {
    object None : CreateUserState()
    object Loading : CreateUserState()
    class Success(val email: String, val username: String) : CreateUserState()
    class Error(val message: String) : CreateUserState()

    fun toStatefulButtonStatus(): StatefulButtonStatus {
        return when (this) {
            None -> StatefulButtonStatus.NONE
            Loading -> StatefulButtonStatus.LOADING
            is Success -> StatefulButtonStatus.SUCCESS
            is Error -> StatefulButtonStatus.NONE
        }
    }
}
