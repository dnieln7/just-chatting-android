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
import xyz.dnieln7.domain.repository.UserRepository
import xyz.dnieln7.testing.fake.buildUser
import xyz.dnieln7.testing.relaxedMockk

class GetUserByEmailUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val userRepository = relaxedMockk<UserRepository>()
    private val getErrorFromThrowableUseCase = relaxedMockk<GetErrorFromThrowableUseCase>()

    private lateinit var useCase: GetUserByEmailUseCase

    @Before
    fun setup() {
        useCase = GetUserByEmailUseCase(
            userRepository = userRepository,
            getErrorFromThrowableUseCase = getErrorFromThrowableUseCase,
        )
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val email = "email"
        val user = buildUser()

        coEvery { userRepository.getUserByEmail(email) } returns user.right()

        runTest(dispatcher) {
            val result = useCase(email).getOrNull()

            result shouldBeEqualTo user
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN invoke THEN return the expected result`() {
        val email = "email"
        val throwable = buildException()

        coEvery { userRepository.getUserByEmail(email) } returns throwable.left()
        every { getErrorFromThrowableUseCase(throwable) } returns throwable.localizedMessage!!

        runTest(dispatcher) {
            val result = useCase(email).swap().getOrNull()

            result shouldBeEqualTo throwable.localizedMessage
        }
    }
}