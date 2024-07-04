package xyz.dnieln7.domain.usecase

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.validation.ValidationStatus

class ValidateSimpleTextUseCaseTest {

    private lateinit var useCase: ValidateSimpleTextUseCase

    @Before
    fun setup() {
        useCase = ValidateSimpleTextUseCase()
    }

    @Test
    fun `GIVEN the happy path String WHEN invoke THEN return Valid`() {
        val string = "STRING"

        val result = useCase(string)

        result shouldBeEqualTo ValidationStatus.Text.Valid
    }

    @Test
    fun `GIVEN a empty String WHEN invoke THEN return EMPTY`() {
        val string = ""

        val result = useCase(string)

        result shouldBeEqualTo ValidationStatus.Text.Invalid.EMPTY
    }
}