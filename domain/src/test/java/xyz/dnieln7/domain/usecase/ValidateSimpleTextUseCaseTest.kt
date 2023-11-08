package xyz.dnieln7.domain.usecase

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.validation.SimpleTextValidationError

class ValidateSimpleTextUseCaseTest {

    private lateinit var useCase: ValidateSimpleTextUseCase

    @Before
    fun setup() {
        useCase = ValidateSimpleTextUseCase()
    }

    @Test
    fun `GIVEN the happy path String WHEN invoke THEN return Unit`() {
        val string = "STRING"

        val result = useCase(string).getOrNull()

        result shouldBeEqualTo Unit
    }

    @Test
    fun `GIVEN a empty String WHEN invoke THEN return SimpleTextValidationError_EMPTY`() {
        val string = ""

        val result = useCase(string).swap().getOrNull()

        result shouldBeEqualTo SimpleTextValidationError.EMPTY
    }
}