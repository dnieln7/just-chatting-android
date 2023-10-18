package xyz.dnieln7.signup.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import xyz.dnieln7.signup.screen.createpassword.CreatePasswordRoute
import xyz.dnieln7.signup.screen.createuser.CreateUserRoute
import xyz.dnieln7.signup.screen.register.RegisterRoute

fun NavGraphBuilder.signupNavHost(
    navController: NavController,
    route: String,
    navigateToHome: () -> Unit,
) {
    navigation(startDestination = CreateUserDestination.route, route = route) {
        composable(route = CreateUserDestination.route) {
            val signupEntry = remember(it) { navController.getBackStackEntry(route) }

            CreateUserRoute(
                signupViewModel = hiltViewModel(signupEntry),
                navigateToCreatePassword = {
                    CreateUserDestination.navigateToCreatePassword(navController)
                },
            )
        }
        composable(route = CreatePasswordDestination.route) {
            val signupEntry = remember(it) { navController.getBackStackEntry(route) }

            CreatePasswordRoute(
                signupViewModel = hiltViewModel(signupEntry),
                navigateToRegister = { CreatePasswordDestination.navigateToRegister(navController) },
            )
        }
        composable(route = RegisterDestination.route) {
            val signupEntry = remember(it) { navController.getBackStackEntry(route) }
            RegisterRoute(
                signupViewModel = hiltViewModel(signupEntry),
                navigateToHome = navigateToHome,
            )
        }
    }
}
