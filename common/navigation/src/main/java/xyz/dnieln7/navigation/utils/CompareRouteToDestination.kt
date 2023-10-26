package xyz.dnieln7.navigation.utils

import xyz.dnieln7.navigation.NavArgsDestination
import xyz.dnieln7.navigation.NavDestination

fun compareRouteToDestination(route: String, destination: NavDestination): Boolean {
    return if (destination is NavArgsDestination) {
        route == destination.routeWithArgs
    } else {
        route == destination.route
    }
}
