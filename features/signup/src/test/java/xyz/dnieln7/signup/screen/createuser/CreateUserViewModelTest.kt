package xyz.dnieln7.signup.screen.createuser

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
import xyz.dnieln7.domain.usecase.GetEmailAvailabilityUseCase
import xyz.dnieln7.domain.usecase.ValidateEmailUseCase
import xyz.dnieln7.domain.usecase.ValidateSimpleTextUseCase
import xyz.dnieln7.domain.validation.EmailValidationError
import xyz.dnieln7.domain.validation.SimpleTextValidationError
import xyz.dnieln7.testing.relaxedMockk

@OptIn(ExperimentalCoroutinesApi::class)
class CreateUserViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private val validateEmailUseCase = relaxedMockk<ValidateEmailUseCase>()
    private val validateSimpleTextUseCase = relaxedMockk<ValidateSimpleTextUseCase>()
    private val getEmailAvailabilityUseCase = relaxedMockk<GetEmailAvailabilityUseCase>()

    private lateinit var viewModel: CreateUserViewModel

    @Before
    fun setup() {
        viewModel = CreateUserViewModel(dispatcher, validateEmailUseCase, validateSimpleTextUseCase, getEmailAvailabilityUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN nothing THEN initialize the expected state`() {
        viewModel.state.value shouldBeEqualTo CreateUserState.None
    }

    @Test
    fun `GIVEN the happy path WHEN createUser THEN emit the expected states`() {
        val email = "email"
        val username = "username"

        every { validateEmailUseCase(email) } returns Unit.right()
        every { validateSimpleTextUseCase(username) } returns Unit.right()
        coEvery { getEmailAvailabilityUseCase(email) } returns Unit.right()

        runTest(dispatcher) {
            viewModel.createUser(email, username)

            viewModel.state.test {
                awaitItem() shouldBeEqualTo CreateUserState.None
                awaitItem() shouldBeEqualTo CreateUserState.Loading
                awaitItem().let {
                    it shouldBeInstanceOf CreateUserState.Success::class
                    (it as CreateUserState.Success).email shouldBeEqualTo email
                    (it as CreateUserState.Success).username shouldBeEqualTo username
                }
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

            viewModel.state.test {
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
        coEvery { getEmailAvailabilityUseCase(email) } returns error.left()

        runTest(dispatcher) {
            viewModel.createUser(email, username)

            viewModel.state.test {
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
    fun `GIVEN the happy path WHEN resetState THEN emit the expected states`() {
        runTest(dispatcher) {
            viewModel.resetState()

            viewModel.state.test {
                advanceUntilIdle()

                awaitItem() shouldBeEqualTo CreateUserState.None
            }
        }
    }

}