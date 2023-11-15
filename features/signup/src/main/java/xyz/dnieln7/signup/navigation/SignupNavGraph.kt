package xyz.dnieln7.signup.navigation

import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import xyz.dnieln7.navigation.NavArgsDestination
import xyz.dnieln7.navigation.NavDestination

object SignupDestination : NavDestination("just-chatting/signup")

object CreateUserDestination : NavDestination("just-chatting/signup/create-user") {

    fun navigateToCreatePassword(navController: NavController, email: String, username: String) {
        navController.navigate("${CreatePasswordDestination.route}/$email/$username")
    }
}

object CreatePasswordDestination : NavArgsDestination(
    route = "just-chatting/signup/create-password",
    args = listOf(
        navArgument(CREATE_PASSWORD_EMAIL) { type = NavType.StringType },
        navArgument(CREATE_PASSWORD_USERNAME) { type = NavType.StringType },
    ),
) {

    fun navigateToRegister(
        navController: NavController,
        email: String,
        password: String,
        username: String,
    ) {
        navController.navigate("${RegisterDestination.route}/$email/$password/$username")
    }
}

internal const val CREATE_PASSWORD_EMAIL = "create_password_email"
internal const val CREATE_PASSWORD_USERNAME = "create_password_username"

object RegisterDestination : NavArgsDestination(
    route = "just-chatting/signup/register",
    args = listOf(
        navArgument(REGISTER_EMAIL) { type = NavType.StringType },
        navArgument(REGISTER_PASSWORD) { type = NavType.StringType },
        navArgument(REGISTER_USERNAME) { type = NavType.StringType },
    ),
)

internal const val REGISTER_EMAIL = "register_email"
internal const val REGISTER_PASSWORD = "register_password"
internal const val REGISTER_USERNAME = "register_username"
