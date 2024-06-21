package xyz.dnieln7.signup.screen.createuser

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import io.mockk.every
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.usecase.ValidateEmailUseCase
import xyz.dnieln7.domain.usecase.ValidateSimpleTextUseCase
import xyz.dnieln7.testing.relaxedMockk

class CreateUserFormViewModelTest {

    private val validateEmailUseCase = relaxedMockk<ValidateEmailUseCase>()
    private val validateSimpleTextUseCase = relaxedMockk<ValidateSimpleTextUseCase>()

    private lateinit var viewModel: CreateUserFormViewModel

    @Before
    fun setup() {
        viewModel = CreateUserFormViewModel(validateEmailUseCase, validateSimpleTextUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN initialize the expected state`() {
        viewModel.form.value.let {
            it.email shouldBeEqualTo ""
            it.username shouldBeEqualTo ""
        }

        viewModel.validation.value.let {
            it.emailValidation shouldBeEqualTo EmailValidationError.EMPTY
            it.usernameValidation shouldBeEqualTo SimpleTextValidationError.EMPTY
        }
    }

    @Test
    fun `GIVEN the happy path WHEN updateEmail THEN emit the expected states`() {
        val email = "email@example.com"

        every { validateEmailUseCase(email) } returns Unit.right()

        runTest {
            viewModel.updateEmail(email)

            viewModel.form.test {
                awaitItem().email shouldBeEqualTo email
            }

            viewModel.validation.test {
                awaitItem().emailValidation.shouldBeNull()
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN updateEmail THEN emit the expected states`() {
        val email = "NOT_AN_EMAIL"

        every { validateEmailUseCase(email) } returns EmailValidationError.NOT_AN_EMAIL.left()

        runTest {
            viewModel.updateEmail(email)

            viewModel.form.test {
                awaitItem().email shouldBeEqualTo email
            }

            viewModel.validation.test {
                awaitItem().emailValidation shouldBeEqualTo EmailValidationError.NOT_AN_EMAIL
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN updateUsername THEN emit the expected states`() {
        val username = "username"

        every { validateSimpleTextUseCase(username) } returns Unit.right()

        runTest {
            viewModel.updateUsername(username)

            viewModel.form.test {
                awaitItem().username shouldBeEqualTo username
            }

            viewModel.validation.test {
                awaitItem().usernameValidation.shouldBeNull()
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN updateUsername THEN emit the expected states`() {
        val username = "username"

        every { validateSimpleTextUseCase(username) } returns SimpleTextValidationError.EMPTY.left()

        runTest {
            viewModel.updateUsername(username)

            viewModel.form.test {
                awaitItem().username shouldBeEqualTo username
            }

            viewModel.validation.test {
                awaitItem().usernameValidation shouldBeEqualTo SimpleTextValidationError.EMPTY
            }
        }
    }
}