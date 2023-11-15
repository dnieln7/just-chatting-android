package xyz.dnieln7.chats.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.dnieln7.chats.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.chats)) }) }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text("Chats")
        }
    }
}
