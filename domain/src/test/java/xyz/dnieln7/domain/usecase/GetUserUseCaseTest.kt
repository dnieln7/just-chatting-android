package xyz.dnieln7.domain.usecase

import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeTrue
import org.amshove.kluent.shouldNotBeNull
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.preferences.DataStorePreferences
import xyz.dnieln7.testing.fake.buildUser
import xyz.dnieln7.testing.relaxedMockk

class GetUserUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val dataStorePreferences = relaxedMockk<DataStorePreferences>()

    private lateinit var useCase: GetUserUseCase

    @Before
    fun setup() {
        useCase = GetUserUseCase(dataStorePreferences)
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val user = buildUser()

        coEvery { dataStorePreferences.getUser() } returns flowOf(user)

        runTest(dispatcher) {
            val result = useCase().getOrNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo user
        }
    }

    @Test
    fun `GIVEN null WHEN invoke THEN return None`() {
        coEvery { dataStorePreferences.getUser() } returns flowOf(null)

        runTest(dispatcher) {
            val result = useCase()

            result.isNone().shouldBeTrue()
        }
    }

    @Test
    fun `GIVEN emptyFlow WHEN invoke THEN return None`() {
        coEvery { dataStorePreferences.getUser() } returns emptyFlow()

        runTest(dispatcher) {
            val result = useCase()

            result.isNone().shouldBeTrue()
        }
    }
}