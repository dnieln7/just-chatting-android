package xyz.dnieln7.signup.screen.createpassword

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import io.mockk.every
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeNull
import org.amshove.kluent.shouldBeTrue
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.usecase.ValidatePasswordUseCase
import xyz.dnieln7.domain.validation.PasswordValidationError
import xyz.dnieln7.signup.navigation.CREATE_PASSWORD_EMAIL
import xyz.dnieln7.signup.navigation.CREATE_PASSWORD_USERNAME
import xyz.dnieln7.testing.relaxedMockk

class CreatePasswordFormViewModelTest {

    private val savedStateHandle = relaxedMockk<SavedStateHandle>()
    private val validatePasswordUseCase = relaxedMockk<ValidatePasswordUseCase>()

    private val email = "email"
    private val username = "username"

    private lateinit var viewModel: CreatePasswordFormViewModel

    @Before
    fun setup() {
        every { savedStateHandle.get<String>(CREATE_PASSWORD_EMAIL) } returns email
        every { savedStateHandle.get<String>(CREATE_PASSWORD_USERNAME) } returns username

        viewModel = CreatePasswordFormViewModel(savedStateHandle, validatePasswordUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN initialize the expected state`() {
        viewModel.form.value shouldBeEqualTo CreatePasswordForm(email, username)
        viewModel.validation.value shouldBeEqualTo CreatePasswordFormValidation()
    }

    @Test
    fun `GIVEN the happy path WHEN updatePassword THEN emit the expected states`() {
        val password = "password"

        every { validatePasswordUseCase(password) } returns Unit.right()

        runTest {
            viewModel.updatePassword(password)

            viewModel.form.test {
                awaitItem().password shouldBeEqualTo password
            }

            viewModel.validation.test {
                awaitItem().passwordValidationError.shouldBeNull()
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN updatePassword THEN emit the expected states`() {
        val password = "passwo"

        every { validatePasswordUseCase(password) } returns PasswordValidationError.TOO_SHORT.left()

        runTest {
            viewModel.updatePassword(password)

            viewModel.form.test {
                awaitItem().password shouldBeEqualTo password
            }

            viewModel.validation.test {
                awaitItem().passwordValidationError shouldBeEqualTo PasswordValidationError.TOO_SHORT
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN updatePasswordConfirm THEN emit the expected states`() {
        val password = "password"
        val passwordConfirm = "password"

        every { validatePasswordUseCase(password) } returns Unit.right()

        viewModel.updatePassword(password)

        runTest {
            viewModel.updatePasswordConfirm(passwordConfirm)

            viewModel.form.test {
                awaitItem().passwordConfirm shouldBeEqualTo passwordConfirm
            }

            viewModel.validation.test {
                awaitItem().passwordsAreEqual.shouldBeTrue()
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN updatePasswordConfirm THEN emit the expected states`() {
        val password = "password"
        val passwordConfirm = "passwordConfirm"

        every { validatePasswordUseCase(password) } returns Unit.right()

        viewModel.updatePassword(password)

        runTest {
            viewModel.updatePasswordConfirm(passwordConfirm)

            viewModel.form.test {
                awaitItem().passwordConfirm shouldBeEqualTo passwordConfirm
            }

            viewModel.validation.test {
                awaitItem().passwordsAreEqual.shouldBeFalse()
            }
        }
    }
}