package xyz.dnieln7.signup.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import xyz.dnieln7.signup.screen.createpassword.CreatePasswordAction
import xyz.dnieln7.signup.screen.createpassword.CreatePasswordFormViewModel
import xyz.dnieln7.signup.screen.createpassword.CreatePasswordScreen
import xyz.dnieln7.signup.screen.createuser.CreateUserAction
import xyz.dnieln7.signup.screen.createuser.CreateUserFormViewModel
import xyz.dnieln7.signup.screen.createuser.CreateUserScreen
import xyz.dnieln7.signup.screen.createuser.CreateUserViewModel
import xyz.dnieln7.signup.screen.register.RegisterAction
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
                form = form,
                validation = validation,
                onAction = {
                    when (it) {
                        is CreateUserAction.OnEmailInput -> {
                            createUserFormViewModel.updateEmail(it.text)
                        }

                        is CreateUserAction.OnUsernameInput -> {
                            createUserFormViewModel.updateUsername(it.text)
                        }

                        is CreateUserAction.OnCreateUserClick -> {
                            createUserViewModel.createUser(it.email, it.username)
                        }

                        is CreateUserAction.OnUserCreated -> {
                            CreateUserDestination.navigateToCreatePassword(
                                navController = navController,
                                email = it.email,
                                username = it.username
                            )
                            createUserViewModel.resetState()
                        }
                    }
                }
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
                onAction = {
                    when (it) {
                        is CreatePasswordAction.OnPasswordInput -> {
                            createPasswordFormViewModel.updatePassword(it.text)
                        }

                        is CreatePasswordAction.OnPasswordConfirmInput -> {
                            createPasswordFormViewModel.updatePasswordConfirm(it.text)
                        }

                        is CreatePasswordAction.OnCreatePasswordClick -> {
                            CreatePasswordDestination.navigateToRegister(
                                navController = navController,
                                email = it.email,
                                password = it.password,
                                username = it.username
                            )
                        }
                    }
                }
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
                onAction = {
                    when (it) {
                        RegisterAction.OnRegisterClick -> registerViewModel.register()
                        RegisterAction.OnUserRegistered -> {
                            navigateToHome()
                            registerViewModel.resetState()
                        }
                    }
                }
            )
        }
    }
}
