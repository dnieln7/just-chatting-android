package xyz.dnieln7.signup.screen.createuser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import xyz.dnieln7.coroutines.di.IO
import xyz.dnieln7.domain.usecase.GetEmailAvailabilityUseCase
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    @IO private val dispatcher: CoroutineDispatcher,
    private val getEmailAvailabilityUseCase: GetEmailAvailabilityUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<CreateUserState>(CreateUserState.None)
    val state get() = _state.asStateFlow()

    fun createUser(email: String, username: String) {
        viewModelScope.launch(dispatcher) {
            _state.emit(CreateUserState.Loading)

            getEmailAvailabilityUseCase(email).fold(
                {
                    _state.emit(CreateUserState.Error(it))
                },
                {
                    _state.emit(CreateUserState.Success(email, username))
                }
            )
        }
    }

    fun resetState() {
        viewModelScope.launch(dispatcher) {
            _state.emit(CreateUserState.None)
        }
    }
}