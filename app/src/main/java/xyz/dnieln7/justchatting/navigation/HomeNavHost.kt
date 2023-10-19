package xyz.dnieln7.justchatting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import xyz.dnieln7.contacts.navigation.ContactsDestination
import xyz.dnieln7.contacts.navigation.contactsNavigation

fun NavGraphBuilder.homeNavHost(
    navController: NavController,
    route: String,
) {
    navigation(startDestination = ContactsDestination.route, route = route) {
        contactsNavigation()
    }
}
