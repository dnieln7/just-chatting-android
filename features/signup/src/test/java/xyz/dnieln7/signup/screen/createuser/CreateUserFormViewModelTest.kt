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
import xyz.dnieln7.domain.validation.EmailValidationError
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
        viewModel.form.value shouldBeEqualTo CreateUserForm()
        viewModel.validation.value shouldBeEqualTo CreateUserFormValidation()
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
                awaitItem().emailValidationError.shouldBeNull()
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
                awaitItem().emailValidationError shouldBeEqualTo EmailValidationError.NOT_AN_EMAIL
            }
        }
    }
}