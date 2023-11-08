package xyz.dnieln7.composable.string

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import xyz.dnieln7.composable.R
import xyz.dnieln7.domain.validation.SimpleTextValidationError

// TODO: 17/10/23 Should be part of textfields behaviour?
@Composable
@ReadOnlyComposable
fun stringFromSimpleTextValidationError(simpleTextValidationError: SimpleTextValidationError?): String? {
    return when (simpleTextValidationError) {
        SimpleTextValidationError.EMPTY -> stringResource(R.string.empty_text_error)
        null -> null
    }
}
