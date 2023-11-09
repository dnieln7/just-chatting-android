package xyz.dnieln7.signup.screen.createpassword

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.usecase.ValidatePasswordsUseCase
import xyz.dnieln7.domain.validation.PasswordsValidationError
import xyz.dnieln7.signup.navigation.CREATE_PASSWORD_EMAIL
import xyz.dnieln7.signup.navigation.CREATE_PASSWORD_USERNAME
import xyz.dnieln7.testing.relaxedMockk

@OptIn(ExperimentalCoroutinesApi::class)
class CreatePasswordViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val savedStateHandle = relaxedMockk<SavedStateHandle>()
    private val validatePasswordsUseCase = relaxedMockk<ValidatePasswordsUseCase>()

    private val email = "email"
    private val username = "username"

    private lateinit var viewModel: CreatePasswordViewModel

    @Before
    fun setup() {
        every { savedStateHandle.get<String>(CREATE_PASSWORD_EMAIL) } returns email
        every { savedStateHandle.get<String>(CREATE_PASSWORD_USERNAME) } returns username

        viewModel = CreatePasswordViewModel(dispatcher, savedStateHandle, validatePasswordsUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN initialize the expected state`() {
        runTest(dispatcher) {
            viewModel.state.value shouldBeEqualTo CreatePasswordState.None
        }
    }

    @Test
    fun `GIVEN the happy path WHEN createPassword THEN emit the expected states`() {
        val password = "password"
        val password2 = "password"

        every { validatePasswordsUseCase(password, password2) } returns Unit.right()

        runTest(dispatcher) {
            viewModel.createPassword(password, password2)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo CreatePasswordState.None
                awaitItem().let {
                    it shouldBeInstanceOf CreatePasswordState.Success::class
                    (it as CreatePasswordState.Success).email shouldBeEqualTo email
                    (it as CreatePasswordState.Success).username shouldBeEqualTo username
                    (it as CreatePasswordState.Success).password shouldBeEqualTo password
                }
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN createPassword THEN emit the expected states`() {
        val password = "password"
        val password2 = "password2"

        every { validatePasswordsUseCase(password, password2) } returns PasswordsValidationError.NOT_EQUAL.left()

        runTest(dispatcher) {
            viewModel.createPassword(password, password2)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo CreatePasswordState.None
                awaitItem().let {
                    it shouldBeInstanceOf CreatePasswordState.Error::class
                    it.asError()?.passwordError.shouldNotBeNull()
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN resetState THEN emit the expected states`() {
        runTest(dispatcher) {
            viewModel.resetState()

            viewModel.state.test {
                advanceUntilIdle()

                awaitItem() shouldBeEqualTo CreatePasswordState.None
            }
        }
    }
}