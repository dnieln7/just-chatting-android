package xyz.dnieln7.profile.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import xyz.dnieln7.navigation.NavDestination
import xyz.dnieln7.profile.screen.ProfileScreen
import xyz.dnieln7.profile.screen.ProfileViewModel

fun NavGraphBuilder.profileNavigation(navigateToLogin: () -> Unit) {
    composable(route = ProfileDestination.route) {
        val profileViewModel = hiltViewModel<ProfileViewModel>()

        val uiState by profileViewModel.state.collectAsStateWithLifecycle()

        ProfileScreen(
            uiState = uiState,
            getUser = profileViewModel::getUser,
            logout = profileViewModel::logout,
            navigateToLogin = navigateToLogin,
        )
    }
}

object ProfileDestination : NavDestination("just-chatting/profile")
