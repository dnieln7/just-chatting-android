package xyz.dnieln7.signup.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import xyz.dnieln7.signup.screen.createpassword.CreatePasswordFormViewModel
import xyz.dnieln7.signup.screen.createpassword.CreatePasswordScreen
import xyz.dnieln7.signup.screen.createuser.CreateUserFormViewModel
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
            val createUserFormViewModel = hiltViewModel<CreateUserFormViewModel>()

            val uiState by createUserViewModel.state.collectAsStateWithLifecycle()
            val form by createUserFormViewModel.form.collectAsStateWithLifecycle()
            val validation by createUserFormViewModel.validation.collectAsStateWithLifecycle()

            CreateUserScreen(
                uiState = uiState,
                createUser = createUserViewModel::createUser,
                resetState = createUserViewModel::resetState,
                form = form,
                validation = validation,
                updateEmail = createUserFormViewModel::updateEmail,
                updateUsername = createUserFormViewModel::updateUsername,
                navigateToCreatePassword = { email, username ->
                    CreateUserDestination.navigateToCreatePassword(navController, email, username)
                },
            )
        }
        composable(
            route = CreatePasswordDestination.routeWithArgs,
            arguments = CreatePasswordDestination.args,
        ) {
            val createPasswordFormViewModel = hiltViewModel<CreatePasswordFormViewModel>()
            val form by createPasswordFormViewModel.form.collectAsStateWithLifecycle()
            val validation by createPasswordFormViewModel.validation.collectAsStateWithLifecycle()

            CreatePasswordScreen(
                form = form,
                validation = validation,
                updatePassword = createPasswordFormViewModel::updatePassword,
                updatePasswordConfirm = createPasswordFormViewModel::updatePasswordConfirm,
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
