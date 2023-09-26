package xyz.dnieln7.justchatting.ui.home.contacts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ContactsRoute() {
    ContactsScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Contacts") }) }
    ) {
        Column(modifier = Modifier.padding(it)) {

        }
    }
}
