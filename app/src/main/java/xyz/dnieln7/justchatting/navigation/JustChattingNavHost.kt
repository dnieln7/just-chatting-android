package xyz.dnieln7.justchatting.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import xyz.dnieln7.login.navigation.loginNavigation
import xyz.dnieln7.signup.navigation.SignupDestination
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
        loginNavigation(
            navigateToSignup = { navController.navigate(SignupDestination.route) },
            navigateToHome = { rootDestination = HomeDestination.route },
        )
        signupNavHost(
            navController = navController,
            navigateToHome = { rootDestination = HomeDestination.route }
        )
        homeNavHost(navController = navController)
    }
}
