package xyz.dnieln7.justchatting.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.justchatting.domain.provider.PreferencesProvider
import xyz.dnieln7.justchatting.domain.repository.AuthRepository
import xyz.dnieln7.justchatting.fake.buildException
import xyz.dnieln7.justchatting.fake.buildUser
import xyz.dnieln7.justchatting.mockk.coVerifyOnce
import xyz.dnieln7.justchatting.mockk.relaxedMockk

class SignupUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val preferencesProvider = relaxedMockk<PreferencesProvider>()
    private val authRepository = relaxedMockk<AuthRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: SignupUseCase

    @Before
    fun setup() {
        useCase = SignupUseCase(preferencesProvider, authRepository, getErrorFromThrowableUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN call the expected functions`() {
        val email = "email"
        val password = "password"
        val username = "username"

        val user = buildUser()

        coEvery { authRepository.signup(email, password, username) } returns user.right()

        runTest(dispatcher) {
            useCase(email, password, username).getOrNull()

            coVerifyOnce { preferencesProvider.setUser(user) }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val email = "email"
        val password = "password"
        val username = "username"

        val user = buildUser()

        coEvery { authRepository.signup(email, password, username) } returns user.right()

        runTest(dispatcher) {
            val result = useCase(email, password, username).getOrNull()

            result shouldBeEqualTo Unit
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN invoke THEN return the expected result`() {
        val email = "email"
        val password = "password"
        val username = "username"

        val throwable = buildException()

        coEvery { authRepository.signup(email, password, username) } returns throwable.left()
        coEvery { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase(email, password, username).swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}