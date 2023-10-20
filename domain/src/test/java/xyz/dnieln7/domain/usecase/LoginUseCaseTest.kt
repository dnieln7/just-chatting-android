package xyz.dnieln7.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.fake.buildException
import xyz.dnieln7.domain.preferences.DataStorePreferences
import xyz.dnieln7.domain.repository.AuthRepository
import xyz.dnieln7.testing.coVerifyOnce
import xyz.dnieln7.testing.fake.buildUser
import xyz.dnieln7.testing.relaxedMockk

class LoginUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val authRepository = relaxedMockk<AuthRepository>()
    private val dataStorePreferences = relaxedMockk<DataStorePreferences>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: LoginUseCase

    @Before
    fun setup() {
        useCase = LoginUseCase(dataStorePreferences, authRepository, getErrorFromThrowableUseCase)
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
                dataStorePreferences.setUser(user)
            }
        }
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val email = "email"
        val password = "password"

        val user = buildUser()

        coEvery { authRepository.login(email, password) } returns user.right()

        runTest(dispatcher) {
            val result = useCase(email, password).getOrNull()

            result shouldBeEqualTo Unit
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN invoke THEN return the expected result`() {
        val email = "email"
        val password = "password"

        val throwable = buildException()

        coEvery { authRepository.login(email, password) } returns throwable.left()
        every { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase(email, password).swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}