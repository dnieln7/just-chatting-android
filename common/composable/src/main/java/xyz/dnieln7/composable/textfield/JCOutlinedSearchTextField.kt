package xyz.dnieln7.composable.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import xyz.dnieln7.composable.theme.JCTheme

@Composable
fun JCOutlinedSearchTextField(
    modifier: Modifier = Modifier,
    placeholder: String,
    icon: ImageVector = Icons.Rounded.Search,
    onSearch: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    var query by rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        value = query,
        onValueChange = { query = it },
        placeholder = { Text(placeholder) },
        shape = RoundedCornerShape(percent = 50),
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isFocused) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface,
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                val searchQuery = query.trim()

                if (searchQuery.isNotEmpty()) {
                    onSearch(searchQuery)
                    focusManager.clearFocus()
                }
            }
        ),
        interactionSource = interactionSource,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun JCOutlinedSearchTextFieldPreview() {
    JCTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                JCOutlinedSearchTextField(
                    placeholder = "JCOutlinedSearchTextFieldPreview",
                    onSearch = {}
                )
            }
        }
    }
}
