package xyz.dnieln7.login.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.login.screen.LoginScreen
import xyz.dnieln7.login.screen.LoginViewModel
import xyz.dnieln7.navigation.NavDestination

fun NavGraphBuilder.loginNavigation(
    navigateToSignup: () -> Unit,
    navigateToHome: () -> Unit,
) {
    composable(route = LoginDestination.route) {
        val loginViewModel = hiltViewModel<LoginViewModel>()

        val uiState by loginViewModel.state.collectAsStateWithLifecycle()

        LoginScreen(
            uiState = uiState,
            login = loginViewModel::login,
            onLoggedIn = {
                navigateToHome()
                loginViewModel.onLoggedIn()
            },
            navigateToSignup = navigateToSignup,
        )
    }
}

object LoginDestination : NavDestination("just-chatting/login")
