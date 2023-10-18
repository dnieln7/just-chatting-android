package xyz.dnieln7.signup.navigation

import androidx.navigation.NavController
import xyz.dnieln7.navigation.NavDestination

object CreateUserDestination : NavDestination("just-chatting/signup/create-user") {

    fun navigateToCreatePassword(navController: NavController) {
        navController.navigate(CreatePasswordDestination.route)
    }
}

object CreatePasswordDestination : NavDestination("just-chatting/signup/create-password") {

    fun navigateToRegister(navController: NavController) {
        navController.navigate(RegisterDestination.route)
    }
}

object RegisterDestination : NavDestination("just-chatting/signup/register")
