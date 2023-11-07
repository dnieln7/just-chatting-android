package xyz.dnieln7.profile.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.usecase.GetUserUserCase
import xyz.dnieln7.domain.usecase.LogoutUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val getUserUserCase: GetUserUserCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Loading)
    val state get() = _state.asStateFlow()

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch(dispatcher) {
            _state.emit(ProfileState.Loading)

            getUserUserCase().fold(
                {
                    _state.emit(ProfileState.UserNotFound)
                },
                {
                    _state.emit(ProfileState.UserFound(it))
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch(dispatcher) {
            _state.emit(ProfileState.Loading)

            logoutUseCase().fold(
                {
                    _state.emit(ProfileState.LogoutError(it))
                },
                {
                    _state.emit(ProfileState.LoggedOut)
                }
            )
        }
    }
}