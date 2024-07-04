package xyz.dnieln7.domain.usecase

import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import xyz.dnieln7.domain.validation.ValidationStatus

class ValidatePasswordUseCaseTest {

    private lateinit var useCase: ValidatePasswordUseCase

    @Before
    fun setup() {
        useCase = ValidatePasswordUseCase()
    }

    @Test
    fun `GIVEN the happy path WHEN invoke THEN return Valid`() {
        val password = "123456789012"

        val result = useCase(password)

        result shouldBeEqualTo ValidationStatus.Password.Valid
    }

    @Test
    fun `GIVEN an empty password WHEN invoke THEN return EMPTY`() {
        val password = ""

        val result = useCase(password)

        result shouldBeEqualTo ValidationStatus.Password.Invalid.EMPTY
    }

    @Test
    fun `GIVEN a password with less than MINIMUM_PASSWORD_LENGTH characters WHEN invoke THEN return TOO_SHORT`() {
        val password = "1234567"

        val result = useCase(password)

        result shouldBeEqualTo ValidationStatus.Password.Invalid.TOO_SHORT
    }
}