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
import xyz.dnieln7.justchatting.domain.provider.ResourceProvider
import xyz.dnieln7.justchatting.domain.repository.AuthRepository
import xyz.dnieln7.justchatting.fake.buildException
import xyz.dnieln7.justchatting.fake.buildHttpException
import xyz.dnieln7.justchatting.mockk.relaxedMockk

class GetEmailAvailabilityUserCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val resourceProvider = relaxedMockk<ResourceProvider>()
    private val authRepository = relaxedMockk<AuthRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: GetEmailAvailabilityUserCase

    @Before
    fun setup() {
        useCase = GetEmailAvailabilityUserCase(
            resourceProvider = resourceProvider,
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
    fun `GIVEN an HttpException WHEN invoke THEN return the expected result`() {
        val email = "email"

        val string = "HttpException"
        val throwable = buildHttpException(409)

        coEvery { authRepository.getEmailAvailability(email) } returns throwable.left()
        coEvery { resourceProvider.getString(any()) } returns string

        runTest(dispatcher) {
            val result = useCase(email).swap().getOrNull()

            result shouldBeEqualTo string
        }
    }

    @Test
    fun `GIVEN a Throwable path WHEN invoke THEN return the expected result`() {
        val email = "email"

        val throwable = buildException()

        coEvery { authRepository.getEmailAvailability(email) } returns throwable.left()
        coEvery { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase(email).swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}