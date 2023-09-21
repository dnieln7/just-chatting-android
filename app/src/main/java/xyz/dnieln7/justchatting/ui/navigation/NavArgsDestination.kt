package xyz.dnieln7.justchatting.ui.navigation

import androidx.navigation.NamedNavArgument

abstract class NavArgsDestination(
    route: String,
    val args: List<NamedNavArgument>,
) : NavDestination(route) {
    val routeWithArgs = run {
        return@run if (args.isEmpty()) {
            route
        } else {
            route.plus("/").plus(
                args.joinToString("/") { "{${it.name}}" }
            )
        }
    }

    fun getArgNameAt(index: Int): String {
        return args[index].name
    }
}