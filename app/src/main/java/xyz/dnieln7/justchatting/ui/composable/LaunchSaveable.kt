package xyz.dnieln7.justchatting.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope

@Composable
fun launchSaveable(block: suspend CoroutineScope.() -> Unit): @Composable () -> Unit {
    val (isWorking, setIsWorking) = rememberSaveable { mutableStateOf(false) }

    if (!isWorking) {
        LaunchedEffect(Unit) {
            block()
            setIsWorking(true)
        }
    }

    return {
        if (isWorking) {
            LaunchedEffect(Unit) {
                setIsWorking(false)
            }
        }
    }
}
