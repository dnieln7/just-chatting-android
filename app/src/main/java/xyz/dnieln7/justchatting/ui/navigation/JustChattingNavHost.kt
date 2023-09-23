package xyz.dnieln7.justchatting.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.dnieln7.justchatting.ui.login.LoginRoute
import xyz.dnieln7.justchatting.ui.signup.SignupRoute

@Composable
fun JustChattingNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = LoginDestination.route) {
            LoginRoute(
                navigateToHome = { LoginDestination.navigateToContacts(navController) },
                navigateToSignup = { LoginDestination.navigateToSignup(navController) },
            )
        }
        composable(route = SignupDestination.route) {
            SignupRoute(
                navigateToHome = { LoginDestination.navigateToContacts(navController) },
            )
        }
    }
}
