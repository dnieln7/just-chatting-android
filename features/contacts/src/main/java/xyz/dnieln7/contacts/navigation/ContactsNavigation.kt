package xyz.dnieln7.contacts.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.contacts.screen.ContactsRoute
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.contactsNavigation() {
    composable(route = ContactsDestination.route) {
        ContactsRoute()
    }
}

object ContactsDestination : NavDestination("just-chatting/contacts")
