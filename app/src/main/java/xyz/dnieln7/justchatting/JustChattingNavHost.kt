package xyz.dnieln7.justchatting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.dnieln7.justchatting.ui.home.homeNavGraph
import xyz.dnieln7.login.screen.LoginRoute
import xyz.dnieln7.signup.navigation.signupNavHost

@Composable
fun JustChattingNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
) {
    var rootDestination by rememberSaveable { mutableStateOf(startDestination) }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = rootDestination
    ) {
        composable(route = LoginDestination.route) {
            LoginRoute(
                navigateToSignup = { LoginDestination.navigateToSignup(navController) },
                navigateToHome = {
                    LoginDestination.navigateToHome(navController)
                    rootDestination = HomeDestination.route
                },
            )
        }
        signupNavHost(
            navController = navController,
            route = SignupDestination.route,
            navigateToHome = {
                SignupDestination.navigateToHome(navController)
                rootDestination = HomeDestination.route
            }
        )
        homeNavGraph(
            navController = navController,
            route = HomeDestination.route,
        )
    }
}
