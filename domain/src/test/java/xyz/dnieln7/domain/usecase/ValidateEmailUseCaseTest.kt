package xyz.dnieln7.domain.usecase

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.validation.ValidationStatus

class ValidateEmailUseCaseTest {

    private lateinit var useCase: ValidateEmailUseCase

    @Before
    fun setup() {
        useCase = ValidateEmailUseCase()
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return Valid`() {
        val email = "example@gmail.com"

        val result = useCase(email)

        result shouldBeEqualTo ValidationStatus.Email.Valid
    }

    @Test
    fun `GIVEN an empty email WHEN invoke THEN return EMPTY`() {
        val email = ""

        val result = useCase(email)

        result shouldBeEqualTo ValidationStatus.Email.Invalid.EMPTY
    }

    @Test
    fun `GIVEN wrong email WHEN invoke THEN return MALFORMED`() {
        val email = "@@asdlkcom"

        val result = useCase(email)

        result shouldBeEqualTo ValidationStatus.Email.Invalid.MALFORMED
    }
}