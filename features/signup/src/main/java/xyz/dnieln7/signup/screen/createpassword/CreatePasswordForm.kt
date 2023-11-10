package xyz.dnieln7.signup.screen.createpassword

data class CreatePasswordForm(
    val email: String,
    val username: String,
    val password: String = "",
    val passwordConfirm: String = "",
)
