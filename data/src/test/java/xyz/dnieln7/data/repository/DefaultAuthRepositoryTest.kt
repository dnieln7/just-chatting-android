package xyz.dnieln7.data.repository

import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import xyz.dnieln7.data.exception.EmailNotAvailableException
import xyz.dnieln7.data.server.JustChattingApiService
import xyz.dnieln7.data.server.model.EmailAvailabilitySvModel
import xyz.dnieln7.domain.provider.ResourceProvider
import xyz.dnieln7.testing.relaxedMockk

class DefaultAuthRepositoryTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val justChattingApiService = relaxedMockk<JustChattingApiService>()
    private val resourceProvider = relaxedMockk<ResourceProvider>()

    private lateinit var repository: DefaultAuthRepository

    @Before
    fun setup() {
        repository = DefaultAuthRepository(justChattingApiService, resourceProvider)
    }

    @Test
    fun `GIVEN a 409 code WHEN getEmailAvailability THEN return the expected result`() {
        val email = "email@example.com"
        val error = "email duplicated"
        val throwable = HttpException(Response.error<Void>(409, "".toResponseBody()))

        coEvery { justChattingApiService.getEmailAvailability(EmailAvailabilitySvModel(email)) } throws throwable
        every { resourceProvider.getString(any()) } returns error

        runTest(dispatcher) {
            val result = repository.getEmailAvailability(email).swap().getOrNull()

            result shouldBeInstanceOf EmailNotAvailableException::class
            result?.message shouldBeEqualTo error
        }
    }
}