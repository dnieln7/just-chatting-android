package xyz.dnieln7.login.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.login.screen.LoginAction
import xyz.dnieln7.login.screen.LoginFormViewModel
import xyz.dnieln7.login.screen.LoginScreen
import xyz.dnieln7.login.screen.LoginViewModel
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.loginNavigation(
    navigateToSignup: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable(route = LoginDestination.route) {
        val loginViewModel = hiltViewModel<LoginViewModel>()
        val loginFormViewModel = hiltViewModel<LoginFormViewModel>()

        val uiState by loginViewModel.state.collectAsStateWithLifecycle()
        val form by loginFormViewModel.form.collectAsStateWithLifecycle()
        val validation by loginFormViewModel.validation.collectAsStateWithLifecycle()

        LoginScreen(
            uiState = uiState,
            form = form,
            validation = validation,
            onAction = {
                when (it) {
                    LoginAction.OnSignupClick -> navigateToSignup()
                    is LoginAction.OnEmailInput -> loginFormViewModel.updateEmail(it.text)
                    is LoginAction.OnPasswordInput -> loginFormViewModel.updatePassword(it.text)
                    is LoginAction.OnLoginClick -> loginViewModel.login(it.email, it.password)
                    LoginAction.OnLoggedIn -> {
                        navigateToHome()
                        loginViewModel.onLoggedIn()
                    }
                }
            },
        )
    }
}

object LoginDestination : NavDestination("just-chatting/login")
