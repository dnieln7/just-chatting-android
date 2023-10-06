package xyz.dnieln7.justchatting.domain.usecase

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
import xyz.dnieln7.justchatting.domain.provider.PreferencesProvider
import xyz.dnieln7.justchatting.fake.buildUser
import xyz.dnieln7.justchatting.mockk.relaxedMockk

class GetUserUserCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val preferencesProvider = relaxedMockk<PreferencesProvider>()

    private lateinit var useCase: GetUserUserCase

    @Before
    fun setup() {
        useCase = GetUserUserCase(preferencesProvider)
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return the expected result`() {
        val user = buildUser()

        coEvery { preferencesProvider.getUser() } returns flowOf(user)

        runTest(dispatcher) {
            val result = useCase().getOrNull()

            result.shouldNotBeNull()
            result shouldBeEqualTo user
        }
    }

    @Test
    fun `GIVEN null WHEN invoke THEN return None`() {
        coEvery { preferencesProvider.getUser() } returns flowOf(null)

        runTest(dispatcher) {
            val result = useCase()

            result.isNone().shouldBeTrue()
        }
    }

    @Test
    fun `GIVEN emptyFlow WHEN invoke THEN return None`() {
        coEvery { preferencesProvider.getUser() } returns emptyFlow()

        runTest(dispatcher) {
            val result = useCase()

            result.isNone().shouldBeTrue()
        }
    }
}