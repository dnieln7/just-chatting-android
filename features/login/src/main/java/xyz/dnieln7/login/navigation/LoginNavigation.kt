package xyz.dnieln7.login.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.login.screen.LoginRoute
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.loginNavigation(
    navigateToSignup: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable(route = LoginDestination.route) {
        LoginRoute(navigateToSignup = navigateToSignup, navigateToHome = navigateToHome)
    }
}

object LoginDestination : NavDestination("just-chatting/login")
