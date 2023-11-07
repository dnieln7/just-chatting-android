package xyz.dnieln7.domain.usecase

import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBeNull
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.R
import xyz.dnieln7.domain.preferences.DataStorePreferences
import xyz.dnieln7.domain.provider.ResourceProvider
import xyz.dnieln7.testing.fake.buildException
import xyz.dnieln7.testing.relaxedMockk
import xyz.dnieln7.testing.verifyOnce

class LogoutUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val dataStorePreferences = relaxedMockk<DataStorePreferences>()
    private val resourceProvider = relaxedMockk<ResourceProvider>()

    private lateinit var useCase: LogoutUseCase

    @Before
    fun setup() {
        useCase = LogoutUseCase(dataStorePreferences, resourceProvider)
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        runTest(dispatcher) {
            val result = useCase().getOrNull()

            result shouldBeEqualTo Unit
        }
    }

    @Test
    fun `GIVEN the unhappy path WHEN invoke THEN return the expected result`() {
        val throwable = buildException()

        coEvery { dataStorePreferences.clear() } throws throwable

        runTest(dispatcher) {
            val result = useCase().swap().getOrNull()

            result.shouldNotBeNull()

            verifyOnce {
                resourceProvider.getString(R.string.generic_error)
            }
        }
    }
}