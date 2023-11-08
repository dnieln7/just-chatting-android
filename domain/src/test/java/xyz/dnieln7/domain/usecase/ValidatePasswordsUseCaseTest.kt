package xyz.dnieln7.domain.usecase

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.validation.PasswordsValidationError

class ValidatePasswordsUseCaseTest {

    private lateinit var useCase: ValidatePasswordsUseCase

    @Before
    fun setup() {
        useCase = ValidatePasswordsUseCase()
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return Unit`() {
        val password = "123456789012"
        val password2 = "123456789012"

        val result = useCase(password, password2).getOrNull()

        result shouldBeEqualTo Unit
    }

    @Test
    fun `GIVEN an empty password WHEN invoke THEN return PasswordsValidation_EMPTY`() {
        val password = ""
        val password2 = ""

        val result = useCase(password, password2).swap().getOrNull()

        result shouldBeEqualTo PasswordsValidationError.EMPTY
    }

    @Test
    fun `GIVEN a password with less than 12 characters WHEN invoke THEN return PasswordsValidation_LENGHT_LESS_THAN_12`() {
        val password = "12345678901"
        val password2 = "12345678901"

        val result = useCase(password, password2).swap().getOrNull()

        result shouldBeEqualTo PasswordsValidationError.LENGTH_LESS_THAN_12
    }

    @Test
    fun `GIVEN password not equal to password2 WHEN invoke THEN return PasswordsValidation_NOT_EQUAL`() {
        val password = "123456789012"
        val password2 = "1234567890134"

        val result = useCase(password, password2).swap().getOrNull()

        result shouldBeEqualTo PasswordsValidationError.NOT_EQUAL
    }
}