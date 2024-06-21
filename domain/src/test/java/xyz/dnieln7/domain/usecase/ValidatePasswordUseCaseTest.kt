package xyz.dnieln7.domain.usecase

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test

class ValidatePasswordUseCaseTest {

    private lateinit var useCase: ValidatePasswordUseCase

    @Before
    fun setup() {
        useCase = ValidatePasswordUseCase()
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return Unit`() {
        val password = "123456789012"

        val result = useCase(password).getOrNull()

        result shouldBeEqualTo Unit
    }

    @Test
    fun `GIVEN an empty password WHEN invoke THEN return PasswordsValidation_EMPTY`() {
        val password = ""

        val result = useCase(password).swap().getOrNull()

        result shouldBeEqualTo PasswordValidationError.EMPTY
    }

    @Test
    fun `GIVEN a password with less than MINIMUM_PASSWORD_LENGTH characters WHEN invoke THEN return PasswordsValidation_TOO_SHORT`() {
        val password = "1234567"

        val result = useCase(password).swap().getOrNull()

        result shouldBeEqualTo PasswordValidationError.TOO_SHORT
    }
}