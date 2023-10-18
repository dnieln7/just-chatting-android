package xyz.dnieln7.justchatting.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import xyz.dnieln7.justchatting.ui.home.contacts.ContactsRoute
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    route: String,
) {
    navigation(startDestination = ContactsDestination.route, route = route) {
        composable(route = ContactsDestination.route) {
            ContactsRoute()
        }
    }
}

object ContactsDestination : NavDestination("just-chatting/contacts")
