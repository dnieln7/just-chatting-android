package xyz.dnieln7.justchatting.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

object LoginDestination : NavDestination("just-chatting/login") {

    fun navigateToSignup(navController: NavController) {
        navController.navigate(SignupStep1Destination.route)
    }

    fun navigateToContacts(navController: NavController) {
        navController.navigate(ContactsDestination.route)
    }
}

object SignupStep1Destination : NavDestination("just-chatting/signup/step1") {

    fun navigateToSignupStep2(navController: NavController, username: String, email: String) {
        navController.navigate("${SignupStep2Destination.route}/$username/$email")
    }
}

object SignupStep2Destination : NavArgsDestination(
    route = "just-chatting/signup/step2",
    args = listOf(
        navArgument("username") { type = NavType.StringType },
        navArgument("email") { type = NavType.StringType }
    )
) {

    fun navigateToSignupFinishDestination(navController: NavController) {
        navController.navigate(SignupFinishDestination.route)
    }
}

object SignupFinishDestination : NavDestination("just-chatting/signup/finish") {

    fun navigateToContacts(navController: NavController) {
        navController.navigate(ContactsDestination.route)
    }
}

object ContactsDestination : NavDestination("just-chatting/contacts")
