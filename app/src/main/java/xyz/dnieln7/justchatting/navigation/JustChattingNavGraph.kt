package xyz.dnieln7.justchatting.navigation

import androidx.navigation.NavController
import xyz.dnieln7.navigation.NavDestination

object LoginDestination : NavDestination("just-chatting/login") {

    fun navigateToSignup(navController: NavController) {
        navController.navigate(SignupDestination.route)
    }

    fun navigateToHome(navController: NavController) {
        navController.navigate(HomeDestination.route)
    }
}

object SignupDestination : NavDestination("just-chatting/signup") {

    fun navigateToHome(navController: NavController) {
        navController.navigate(HomeDestination.route)
    }
}

object HomeDestination : NavDestination("just-chatting/home")
