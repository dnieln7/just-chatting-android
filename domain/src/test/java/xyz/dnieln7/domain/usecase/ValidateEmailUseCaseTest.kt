package xyz.dnieln7.domain.usecase

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.justchatting.domain.validation.EmailValidationError

class ValidateEmailUseCaseTest {

    private lateinit var useCase: ValidateEmailUseCase

    @Before
    fun setup() {
        useCase = ValidateEmailUseCase()
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return Unit`() {
        val email = "example@gmail.com"

        val result = useCase(email).getOrNull()

        result shouldBeEqualTo Unit
    }

    @Test
    fun `GIVEN an empty email WHEN invoke THEN return EmailValidationError_EMPTY`() {
        val email = ""

        val result = useCase(email).swap().getOrNull()

        result shouldBeEqualTo EmailValidationError.EMPTY
    }

    @Test
    fun `GIVEN wrong email WHEN invoke THEN return EmailValidationError_NOT_AN_EMAIL`() {
        val email = "@@asdlkcom"

        val result = useCase(email).swap().getOrNull()

        result shouldBeEqualTo EmailValidationError.NOT_AN_EMAIL
    }
}