package xyz.dnieln7.justchatting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import xyz.dnieln7.contacts.navigation.ContactsDestination
import xyz.dnieln7.contacts.navigation.contactsNavigation
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.homeNavHost(
    navController: NavController,
) {
    navigation(startDestination = ContactsDestination.route, route = HomeDestination.route) {
        contactsNavigation()
    }
}

object HomeDestination : NavDestination("just-chatting/home")
