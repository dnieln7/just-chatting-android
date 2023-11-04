package xyz.dnieln7.composable.pullrefresh

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import xyz.dnieln7.composable.theme.JustChattingTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun JustChattingPullRefresh(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    content: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = coroutineScope.launch {
        refreshing = true
        onRefresh()
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    Box(
        modifier = Modifier
            .pullRefresh(refreshState)
            .then(modifier)
    ) {
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = refreshing,
            state = refreshState,
        )
        content()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JustChattingPullRefreshPreview() {
    JustChattingTheme {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                JustChattingPullRefresh(
                    onRefresh = {},
                    content = { Text("CONTENT") }
                )
            }
        }
    }
}
