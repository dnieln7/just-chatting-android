package xyz.dnieln7.justchatting.ui.signup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SignupRoute(
    signupViewModel: SignupViewModel = viewModel(),
    navigateToHome: () -> Unit,
) {
    val uiState by signupViewModel.state.collectAsStateWithLifecycle()

    SignupScreen(
        uiState = uiState,
        createUser = signupViewModel::createUser,
        createPassword = signupViewModel::createPassword,
        register = signupViewModel::register,
        navigateToHome = navigateToHome,
    )
}

@Composable
fun SignupScreen(
    uiState: SignupState,
    createUser: (email: String, username: String) -> Unit,
    createPassword: (password: String, password2: String) -> Unit,
    register: () -> Unit,
    navigateToHome: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        when (uiState) {
            is SignupState.CreateUser -> CreateUserScreen(
                createUserState = uiState,
                createUser = createUser,
            )

            is SignupState.CreatePassword -> CreatePasswordScreen(
                createPasswordState = uiState,
                createPassword = createPassword,
            )

            is SignupState.Register -> RegisterScreen(
                registerState = uiState,
                onRegistered = {},
                retry = register,
            )
        }
    }
}
