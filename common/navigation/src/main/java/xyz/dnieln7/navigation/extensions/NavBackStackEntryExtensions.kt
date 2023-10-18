package xyz.dnieln7.navigation.extensions

import androidx.navigation.NavBackStackEntry

fun NavBackStackEntry.getString(key: String): String {
    return arguments?.getString(key) ?: DEFAULT_ARG_STRING
}

private const val DEFAULT_ARG_STRING = ""
