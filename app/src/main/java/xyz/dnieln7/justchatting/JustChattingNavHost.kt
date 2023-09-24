package xyz.dnieln7.justchatting

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.dnieln7.justchatting.ui.login.LoginRoute
import xyz.dnieln7.justchatting.ui.signup.signupNavGraph

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
        signupNavGraph(
            navController = navController,
            route = SignupDestination.route,
            navigateToHome = { SignupDestination.navigateToContacts(navController) }
        )
    }
}
