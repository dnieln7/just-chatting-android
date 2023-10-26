package xyz.dnieln7.navigation.extensions

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import xyz.dnieln7.navigation.NavDestination

fun NavController.navigateTo(destination: NavDestination) {
    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
