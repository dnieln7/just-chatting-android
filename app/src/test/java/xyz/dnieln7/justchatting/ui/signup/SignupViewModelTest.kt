package xyz.dnieln7.justchatting.ui.signup

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
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
import xyz.dnieln7.justchatting.domain.usecase.GetEmailAvailabilityUserCase
import xyz.dnieln7.justchatting.domain.usecase.SignupUseCase
import xyz.dnieln7.justchatting.domain.usecase.ValidateEmailUseCase
import xyz.dnieln7.justchatting.domain.usecase.ValidatePasswordsUseCase
import xyz.dnieln7.justchatting.domain.usecase.ValidateSimpleTextUseCase
import xyz.dnieln7.justchatting.domain.validation.EmailValidationError
import xyz.dnieln7.justchatting.domain.validation.PasswordsValidationError
import xyz.dnieln7.justchatting.domain.validation.SimpleTextValidationError
import xyz.dnieln7.justchatting.mockk.relaxedMockk
import xyz.dnieln7.justchatting.ui.signup.createpassword.CreatePasswordState
import xyz.dnieln7.justchatting.ui.signup.createuser.CreateUserState
import xyz.dnieln7.justchatting.ui.signup.register.RegisterState

@OptIn(ExperimentalCoroutinesApi::class)
class SignupViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val validateEmailUseCase = relaxedMockk<ValidateEmailUseCase>()
    private val validatePasswordsUseCase = relaxedMockk<ValidatePasswordsUseCase>()
    private val validateSimpleTextUseCase = relaxedMockk<ValidateSimpleTextUseCase>()
    private val getEmailAvailabilityUserCase = relaxedMockk<GetEmailAvailabilityUserCase>()
    private val signupUseCase = relaxedMockk<SignupUseCase>()

    private lateinit var viewModel: SignupViewModel

    @Before
    fun setup() {
        viewModel = SignupViewModel(
            dispatcher = dispatcher,
            validateEmailUseCase = validateEmailUseCase,
            validatePasswordsUseCase = validatePasswordsUseCase,
            validateSimpleTextUseCase = validateSimpleTextUseCase,
            getEmailAvailabilityUserCase = getEmailAvailabilityUserCase,
            signupUseCase = signupUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN initialize the expected state`() {
        viewModel.createUserState.value shouldBeEqualTo CreateUserState.None
        viewModel.createPasswordState.value shouldBeEqualTo CreatePasswordState.None
        viewModel.registerState.value shouldBeEqualTo RegisterState.Loading
    }

    @Test
    fun `GIVEN the happy path WHEN createUser THEN emit the expected states`() {
        val email = "email"
        val username = "username"

        every { validateEmailUseCase(email) } returns Unit.right()
        every { validateSimpleTextUseCase(username) } returns Unit.right()
        coEvery { getEmailAvailabilityUserCase(email) } returns Unit.right()

        runTest(dispatcher) {
            viewModel.createUser(email, username)

            viewModel.createUserState.test {
                awaitItem() shouldBeEqualTo CreateUserState.None
                awaitItem() shouldBeEqualTo CreateUserState.Loading
                awaitItem() shouldBeEqualTo CreateUserState.Success
            }
        }
    }

    @Test
    fun `GIVEN validation errors WHEN createUser THEN emit the expected states`() {
        val email = "email"
        val username = "username"

        every { validateEmailUseCase(email) } returns EmailValidationError.EMPTY.left()
        every { validateSimpleTextUseCase(username) } returns SimpleTextValidationError.EMPTY.left()

        runTest(dispatcher) {
            viewModel.createUser(email, username)

            viewModel.createUserState.test {
                awaitItem() shouldBeEqualTo CreateUserState.None
                awaitItem() shouldBeEqualTo CreateUserState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf CreateUserState.Error::class
                    it.asError()?.emailError.shouldNotBeNull()
                    it.asError()?.usernameError.shouldNotBeNull()
                }
            }
        }
    }

    @Test
    fun `GIVEN not available email WHEN createUser THEN emit the expected states`() {
        val email = "email"
        val username = "username"

        val error = "Not available"

        every { validateEmailUseCase(any()) } returns Unit.right()
        every { validateSimpleTextUseCase(any()) } returns Unit.right()
        coEvery { getEmailAvailabilityUserCase(email) } returns error.left()

        runTest(dispatcher) {
            viewModel.createUser(email, username)

            viewModel.createUserState.test {
                awaitItem() shouldBeEqualTo CreateUserState.None
                awaitItem() shouldBeEqualTo CreateUserState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf CreateUserState.Error::class
                    it.asError()?.error shouldBeEqualTo error
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN onUserCreated THEN emit the expected states`() {
        runTest(dispatcher) {
            viewModel.onUserCreated()

            viewModel.createUserState.test {
                advanceUntilIdle()

                awaitItem() shouldBeEqualTo CreateUserState.None
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN createPassword THEN emit the expected states`() {
        val password = "password"
        val password2 = "password"

        every { validatePasswordsUseCase(password, password2) } returns Unit.right()

        runTest(dispatcher) {
            viewModel.createPassword(password, password2)

            viewModel.createPasswordState.test {
                awaitItem() shouldBeEqualTo CreatePasswordState.None
                awaitItem() shouldBeEqualTo CreatePasswordState.Success
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

            viewModel.createPasswordState.test {
                awaitItem() shouldBeEqualTo CreatePasswordState.None
                awaitItem().let {
                    it shouldBeInstanceOf CreatePasswordState.Error::class
                    it.asError()?.passwordError.shouldNotBeNull()
                }
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN onPasswordCreated THEN emit the expected states`() {
        runTest(dispatcher) {
            viewModel.onPasswordCreated()

            viewModel.createPasswordState.test {
                advanceUntilIdle()

                awaitItem() shouldBeEqualTo CreatePasswordState.None
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN register THEN emit the expected states`() {
        every { validateEmailUseCase(any()) } returns Unit.right()
        every { validateSimpleTextUseCase(any()) } returns Unit.right()
        coEvery { getEmailAvailabilityUserCase(any()) } returns Unit.right()
        every { validatePasswordsUseCase(any(), any()) } returns Unit.right()

        viewModel.createUser("", "")
        viewModel.createPassword("", "")

        coEvery { signupUseCase(any(), any(), any()) } returns Unit.right()

        runTest(dispatcher) {
            viewModel.register()

            viewModel.registerState.test {
                awaitItem() shouldBeEqualTo RegisterState.Loading
                awaitItem() shouldBeEqualTo RegisterState.Success
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN register THEN emit the expected states`() {
        val error = "error"

        every { validateEmailUseCase(any()) } returns Unit.right()
        every { validateSimpleTextUseCase(any()) } returns Unit.right()
        coEvery { getEmailAvailabilityUserCase(any()) } returns Unit.right()
        every { validatePasswordsUseCase(any(), any()) } returns Unit.right()

        viewModel.createUser("", "")
        viewModel.createPassword("", "")

        coEvery { signupUseCase(any(), any(), any()) } returns error.left()

        runTest(dispatcher) {
            viewModel.register()

            viewModel.registerState.test {
                awaitItem() shouldBeEqualTo RegisterState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf RegisterState.Error::class
                    (it as RegisterState.Error).message shouldBeEqualTo error
                }
            }
        }
    }
}