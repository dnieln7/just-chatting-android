package xyz.dnieln7.login.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
            login = loginViewModel::login,
            form = form,
            validation = validation,
            updateEmail = loginFormViewModel::updateEmail,
            updatePassword = loginFormViewModel::updatePassword,
            navigateToSignup = navigateToSignup,
            navigateToHome = {
                navigateToHome()
                loginViewModel.onLoggedIn()
            },
        )
    }
}

object LoginDestination : NavDestination("just-chatting/login")
