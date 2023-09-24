package xyz.dnieln7.justchatting.framework.validation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import xyz.dnieln7.justchatting.R
import xyz.dnieln7.justchatting.domain.validation.UsernameValidationError

@Composable
@ReadOnlyComposable
fun stringFromUsernameValidationError(usernameValidationError: UsernameValidationError?): String? {
    return when (usernameValidationError) {
        UsernameValidationError.EMPTY -> stringResource(R.string.empty_text_error)
        null -> null
    }
}
