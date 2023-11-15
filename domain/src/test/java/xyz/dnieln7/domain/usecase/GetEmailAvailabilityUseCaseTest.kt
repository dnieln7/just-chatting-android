package xyz.dnieln7.domain.usecase

import arrow.core.left
import arrow.core.right
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.fake.buildException
import xyz.dnieln7.domain.repository.AuthRepository
import xyz.dnieln7.testing.relaxedMockk

class GetEmailAvailabilityUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val authRepository = relaxedMockk<AuthRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: GetEmailAvailabilityUseCase

    @Before
    fun setup() {
        useCase = GetEmailAvailabilityUseCase(
            authRepository = authRepository,
            getErrorFromThrowableUseCase = getErrorFromThrowableUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val email = "email"

        coEvery { authRepository.getEmailAvailability(email) } returns Unit.right()

        runTest(dispatcher) {
            val result = useCase(email).getOrNull()

            result shouldBeEqualTo Unit
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN invoke THEN return the expected result`() {
        val email = "email"

        val throwable = buildException()

        coEvery { authRepository.getEmailAvailability(email) } returns throwable.left()
        every { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase(email).swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}