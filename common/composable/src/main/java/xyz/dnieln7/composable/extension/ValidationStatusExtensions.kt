package xyz.dnieln7.composable.extension

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import xyz.dnieln7.composable.R
import xyz.dnieln7.domain.validation.ValidationStatus

@Composable
@ReadOnlyComposable
fun ValidationStatus.asString(): String? {
    return when (this) {
        ValidationStatus.Email.Invalid.EMPTY -> stringResource(R.string.empty_text_error)
        ValidationStatus.Email.Invalid.MALFORMED -> stringResource(R.string.email_not_valid_error)
        ValidationStatus.Email.Valid -> null
        ValidationStatus.Password.Invalid.EMPTY -> stringResource(R.string.empty_text_error)
        ValidationStatus.Password.Invalid.TOO_SHORT -> stringResource(R.string.password_length_error)
        ValidationStatus.Password.Valid -> null
        ValidationStatus.Text.Invalid.EMPTY -> stringResource(R.string.empty_text_error)
        ValidationStatus.Text.Valid -> null
    }
}

fun ValidationStatus.asString(context: Context): String? {
    return when (this) {
        ValidationStatus.Email.Invalid.EMPTY -> context.getString(R.string.empty_text_error)
        ValidationStatus.Email.Invalid.MALFORMED -> context.getString(R.string.email_not_valid_error)
        ValidationStatus.Email.Valid -> null
        ValidationStatus.Password.Invalid.EMPTY -> context.getString(R.string.empty_text_error)
        ValidationStatus.Password.Invalid.TOO_SHORT -> context.getString(R.string.password_length_error)
        ValidationStatus.Password.Valid -> null
        ValidationStatus.Text.Invalid.EMPTY -> context.getString(R.string.empty_text_error)
        ValidationStatus.Text.Valid -> null
    }
}
