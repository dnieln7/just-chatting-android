package xyz.dnieln7.justchatting.ui.signup

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import xyz.dnieln7.justchatting.ui.navigation.NavDestination
import xyz.dnieln7.justchatting.ui.signup.createpassword.CreatePasswordRoute
import xyz.dnieln7.justchatting.ui.signup.createuser.CreateUserRoute
import xyz.dnieln7.justchatting.ui.signup.register.RegisterRoute

fun NavGraphBuilder.signupNavGraph(
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

object CreateUserDestination : NavDestination("just-chatting/signup/create-user") {

    fun navigateToCreatePassword(navController: NavController) {
        navController.navigate(CreatePasswordDestination.route)
    }
}

object CreatePasswordDestination : NavDestination("just-chatting/signup/create-password") {

    fun navigateToRegister(navController: NavController) {
        navController.navigate(RegisterDestination.route)
    }
}

object RegisterDestination : NavDestination("just-chatting/signup/register")
