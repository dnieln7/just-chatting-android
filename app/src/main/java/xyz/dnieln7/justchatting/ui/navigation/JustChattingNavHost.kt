package xyz.dnieln7.justchatting.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.dnieln7.justchatting.framework.extensions.getString
import xyz.dnieln7.justchatting.ui.login.LoginScreen
import xyz.dnieln7.justchatting.ui.signup.SignupFinishScreen
import xyz.dnieln7.justchatting.ui.signup.SignupState
import xyz.dnieln7.justchatting.ui.signup.SignupStep1Screen
import xyz.dnieln7.justchatting.ui.signup.SignupStep2Screen

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
            LoginScreen(navigateToSignup = { LoginDestination.navigateToSignup(navController) })
        }
        composable(route = SignupStep1Destination.route) {
            SignupStep1Screen(
                navigateToSignupStep2 = { username, email ->
                    SignupStep1Destination.navigateToSignupStep2(navController, username, email)
                }
            )
        }
        composable(
            route = SignupStep2Destination.routeWithArgs,
            arguments = SignupStep2Destination.args,
        ) {
            SignupStep2Screen(
                navigateToSignupFinish = { password ->
                    val username = it.getString(SignupStep2Destination.getArgNameAt(0))
                    val email = it.getString(SignupStep2Destination.getArgNameAt(1))

                    println("username: $username")
                    println("email: $email")
                    println("password: $password")

                    SignupStep2Destination.navigateToSignupFinishDestination(navController)
                }
            )
        }
        composable(route = SignupFinishDestination.route) {
            SignupFinishScreen(SignupState.Loading)
        }
    }
}
