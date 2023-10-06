package xyz.dnieln7.justchatting.domain.usecase

import io.mockk.every
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.justchatting.domain.provider.ResourceProvider
import xyz.dnieln7.justchatting.fake.buildException
import xyz.dnieln7.justchatting.mockk.relaxedMockk

class GetErrorFromThrowableUseCaseTest {

    private val resourceProvider = relaxedMockk<ResourceProvider>()

    private lateinit var useCase: GetErrorFromThrowableUseCase

    @Before
    fun setup() {
        every { resourceProvider.getString(any()) } returns STRING_FROM_RESOURCES

        useCase = GetErrorFromThrowableUseCase(resourceProvider)
    }

    @Test
    fun `GIVEN a Throwable with localizedError WHEN invoke THEN return localizedError`() {
        val throwable = buildException()

        val result = useCase(throwable)

        result shouldBeEqualTo "${throwable.localizedMessage}"
    }

    @Test
    fun `GIVEN a Throwable without localizedError WHEN invoke THEN return localizedError`() {
        val throwable = buildException(message = null)

        val result = useCase(throwable)

        result shouldBeEqualTo STRING_FROM_RESOURCES
    }
}

private const val STRING_FROM_RESOURCES = "string from resources"
