package xyz.dnieln7.signup.screen.register

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.usecase.SignupUseCase
import xyz.dnieln7.signup.navigation.REGISTER_EMAIL
import xyz.dnieln7.signup.navigation.REGISTER_PASSWORD
import xyz.dnieln7.signup.navigation.REGISTER_USERNAME
import xyz.dnieln7.testing.relaxedMockk

class RegisterViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val savedStateHandle = relaxedMockk<SavedStateHandle>()
    private val signupUseCase = relaxedMockk<SignupUseCase>()

    private val email = "email"
    private val password = "password"
    private val username = "username"

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setup() {
        every { savedStateHandle.get<String>(REGISTER_EMAIL) } returns email
        every { savedStateHandle.get<String>(REGISTER_PASSWORD) } returns password
        every { savedStateHandle.get<String>(REGISTER_USERNAME) } returns username
    }

    @Test
    fun `GIVEN the happy path WHEN init THEN emit the expected states`() {
        coEvery { signupUseCase(email, password, username) } returns Unit.right()

        runTest(dispatcher) {
            viewModel = RegisterViewModel(dispatcher, savedStateHandle, signupUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo RegisterState.Loading
                awaitItem() shouldBeEqualTo RegisterState.Success
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN init THEN emit the expected states`() {
        val error = "Error"

        coEvery { signupUseCase(email, password, username) } returns error.left()

        runTest(dispatcher) {
            viewModel = RegisterViewModel(dispatcher, savedStateHandle, signupUseCase)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo RegisterState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf RegisterState.Error::class
                    (it as RegisterState.Error).message shouldBeEqualTo error
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN register THEN emit the expected states`() {
        coEvery { signupUseCase(email, password, username) } returns Unit.right()

        viewModel = RegisterViewModel(dispatcher, savedStateHandle, signupUseCase)

        runTest(dispatcher) {
            viewModel.register()

            viewModel.state.test {
                skipItems(1)
                awaitItem() shouldBeEqualTo RegisterState.Loading
                awaitItem() shouldBeEqualTo RegisterState.Success
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN resetState THEN emit the expected states`() {
        coEvery { signupUseCase(email, password, username) } returns Unit.right()

        viewModel = RegisterViewModel(dispatcher, savedStateHandle, signupUseCase)

        runTest(dispatcher) {
            viewModel.resetState()

            viewModel.state.onEach { println(it) }.test {
                skipItems(1)
                awaitItem() shouldBeEqualTo RegisterState.Loading
            }
        }
    }
}