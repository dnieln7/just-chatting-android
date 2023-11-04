package xyz.dnieln7.composable.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import xyz.dnieln7.composable.theme.JustChattingTheme

@Composable
fun JustChattingTabs(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    onTabChange: (Int) -> Unit,
    selectedTextColor: Color = MaterialTheme.colorScheme.primary,
    unselectedTextColor: Color = MaterialTheme.colorScheme.onSurface,
    indicatorColor: Color = MaterialTheme.colorScheme.primary
) {
    var selectedTabPosition by rememberSaveable { mutableIntStateOf(0) }

    TabRow(
        modifier = modifier,
        containerColor = Color.Transparent,
        selectedTabIndex = selectedTabPosition,
        divider = {},
        indicator = { tabPositions ->
            if (selectedTabPosition < tabPositions.size) {
                TabRowDefaults.Indicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabPosition])
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(50)),
                    color = indicatorColor.copy(alpha = 0.25F),
                )
            }
        },
    ) {
        tabs.forEachIndexed { tabPosition, tab ->
            Tab(
                modifier = Modifier.clip(RoundedCornerShape(50)),
                selected = selectedTabPosition == tabPosition,
                onClick = {
                    if (selectedTabPosition != tabPosition) {
                        selectedTabPosition = tabPosition
                        onTabChange(tabPosition)
                    }
                },
                text = {
                    Text(
                        text = tab,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = if (selectedTabPosition == tabPosition) {
                                selectedTextColor
                            } else {
                                unselectedTextColor
                            }
                        ),
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun JustChattingOutlinedTabsPreview() {
    JustChattingTheme(darkTheme = false) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                JustChattingTabs(tabs = listOf("Home", "Favorites", "Settings"), onTabChange = {})
            }
        }
    }
}
