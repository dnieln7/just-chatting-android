package xyz.dnieln7.justchatting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import xyz.dnieln7.justchatting.ui.theme.JustChattingTheme

class JustChattingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustChattingTheme {
                JustChattingNavHost(startDestination = LoginDestination.route)
            }
        }
    }
}
