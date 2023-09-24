package xyz.dnieln7.justchatting.ui.signup

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
            CreateUserRoute(
                navigateToCreatePassword = {
                    CreateUserDestination.navigateToCreatePassword(navController)
                },
            )
        }
        composable(route = CreatePasswordDestination.route) {
            CreatePasswordRoute(
                navigateToRegister = { CreatePasswordDestination.navigateToRegister(navController) },
            )
        }
        composable(route = RegisterDestination.route) {
            RegisterRoute(navigateToHome = navigateToHome)
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
