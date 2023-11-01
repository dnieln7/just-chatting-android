package xyz.dnieln7.data.repository

import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.data.exception.UserNotFoundException
import xyz.dnieln7.data.fake.buildHttpException
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.domain.provider.ResourceProvider
import xyz.dnieln7.testing.relaxedMockk

class DefaultUserRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val justChattingApiService = relaxedMockk<JustChattingApiService>()
    private val resourceProvider = relaxedMockk<ResourceProvider>()

    private lateinit var repository: DefaultUserRepository

    @Before
    fun setup() {
        repository = DefaultUserRepository(justChattingApiService, resourceProvider)
    }

    @Test
    fun `GIVEN 400 WHEN getUserByEmail THEN return the expected result`() {
        val email = "email@example.com"
        val error = "User not found"
        val throwable = buildHttpException(400)

        coEvery { justChattingApiService.getUserByEmail(email) } throws throwable
        every { resourceProvider.getString(any()) } returns error

        runTest(dispatcher) {
            val result = repository.getUserByEmail(email).swap().getOrNull()

            result shouldBeInstanceOf UserNotFoundException::class
            result?.message shouldBeEqualTo error
        }
    }
}