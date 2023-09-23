package xyz.dnieln7.justchatting.ui.navigation

import androidx.navigation.NavController

object LoginDestination : NavDestination("just-chatting/login") {

    fun navigateToSignup(navController: NavController) {
        navController.navigate(SignupDestination.route)
    }

    fun navigateToContacts(navController: NavController) {
        navController.navigate(ContactsDestination.route)
    }
}

object SignupDestination : NavDestination("just-chatting/signup") {

    fun navigateToContacts(navController: NavController) {
        navController.navigate(ContactsDestination.route)
    }
}

object ContactsDestination : NavDestination("just-chatting/contacts")
