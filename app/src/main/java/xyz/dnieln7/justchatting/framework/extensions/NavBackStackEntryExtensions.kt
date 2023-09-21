package xyz.dnieln7.justchatting.framework.extensions

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry.getString(key: String): String {
    return arguments?.getString(key) ?: DEFAULT_ARG_STRING
}

private const val DEFAULT_ARG_STRING = ""
