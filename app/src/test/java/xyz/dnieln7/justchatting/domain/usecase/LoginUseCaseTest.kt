package xyz.dnieln7.justchatting.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.justchatting.domain.provider.PreferencesProvider
import xyz.dnieln7.justchatting.domain.repository.AuthRepository
import xyz.dnieln7.justchatting.fake.buildException
import xyz.dnieln7.justchatting.fake.buildUser
import xyz.dnieln7.justchatting.mockk.coVerifyOnce
import xyz.dnieln7.justchatting.mockk.relaxedMockk

class LoginUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val authRepository = relaxedMockk<AuthRepository>()
    private val preferencesProvider = relaxedMockk<PreferencesProvider>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: LoginUseCase

    @Before
    fun setup() {
        useCase = LoginUseCase(preferencesProvider, authRepository, getErrorFromThrowableUseCase)
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN call the expected functions`() {
        val email = "email"
        val password = "password"

        val user = buildUser()

        coEvery { authRepository.login(email, password) } returns user.right()

        runTest(dispatcher) {
            useCase(email, password)

            coVerifyOnce {
                preferencesProvider.setUser(user)
            }
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN invoke THEN call the expected functions`() {
        val email = "email"
        val password = "password"

        val throwable = buildException()

        coEvery { authRepository.login(email, password) } returns throwable.left()

        runTest(dispatcher) {
            useCase(email, password)

            coVerifyOnce {
                getErrorFromThrowableUseCase(throwable)
            }
        }
    }
}