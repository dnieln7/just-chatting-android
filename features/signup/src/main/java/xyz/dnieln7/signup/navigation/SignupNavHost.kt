package xyz.dnieln7.signup.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import xyz.dnieln7.signup.screen.createpassword.CreatePasswordScreen
import xyz.dnieln7.signup.screen.createpassword.CreatePasswordViewModel
import xyz.dnieln7.signup.screen.createuser.CreateUserScreen
import xyz.dnieln7.signup.screen.createuser.CreateUserViewModel
import xyz.dnieln7.signup.screen.register.RegisterScreen
import xyz.dnieln7.signup.screen.register.RegisterViewModel

fun NavGraphBuilder.signupNavHost(
    navController: NavController,
    navigateToHome: () -> Unit,
) {
    navigation(startDestination = CreateUserDestination.route, route = SignupDestination.route) {
        composable(route = CreateUserDestination.route) {
            val createUserViewModel = hiltViewModel<CreateUserViewModel>()
            val uiState by createUserViewModel.state.collectAsStateWithLifecycle()

            CreateUserScreen(
                uiState = uiState,
                createUser = createUserViewModel::createUser,
                resetState = createUserViewModel::resetState,
                navigateToCreatePassword = { email, username ->
                    CreateUserDestination.navigateToCreatePassword(navController, email, username)
                }
            )
        }
        composable(
            route = CreatePasswordDestination.routeWithArgs,
            arguments = CreatePasswordDestination.args,
        ) {
            val createPasswordViewModel = hiltViewModel<CreatePasswordViewModel>()
            val uiState by createPasswordViewModel.state.collectAsStateWithLifecycle()

            CreatePasswordScreen(
                uiState = uiState,
                createPassword = createPasswordViewModel::createPassword,
                resetState = createPasswordViewModel::resetState,
                navigateToRegister = { email, password, username ->
                    CreatePasswordDestination.navigateToRegister(
                        navController = navController,
                        email = email,
                        password = password,
                        username = username
                    )
                },
            )
        }
        composable(
            route = RegisterDestination.routeWithArgs,
            arguments = RegisterDestination.args,
        ) {
            val registerViewModel = hiltViewModel<RegisterViewModel>()
            val uiState by registerViewModel.state.collectAsStateWithLifecycle()

            RegisterScreen(
                uiState = uiState,
                navigateToHome = navigateToHome,
                register = registerViewModel::register,
                resetState = registerViewModel::resetState,
            )
        }
    }
}
