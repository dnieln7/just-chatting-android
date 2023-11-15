package xyz.dnieln7.justchatting.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.Contacts
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import xyz.dnieln7.chats.navigation.ChatsDestination
import xyz.dnieln7.chats.navigation.chatsNavigation
import xyz.dnieln7.friendships.navigation.FriendshipsDestination
import xyz.dnieln7.friendships.navigation.friendshipsNavigation
import xyz.dnieln7.navigation.NavDestination
import xyz.dnieln7.navigation.extensions.navigateTo
import xyz.dnieln7.navigation.utils.compareRouteToDestination
import xyz.dnieln7.profile.navigation.ProfileDestination
import xyz.dnieln7.profile.navigation.profileNavigation

@Composable
fun HomeNavHost(onLoggedOut: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    Scaffold(
        bottomBar = {
            NavigationBar {
                JustChattingNavigationBarItem(
                    label = stringResource(xyz.dnieln7.friendships.R.string.friendships),
                    icon = Icons.Rounded.Contacts,
                    destination = FriendshipsDestination,
                    onClick = { navController.navigateTo(it) },
                    isSelected = { compareRouteToDestination(currentRoute, it) }
                )
                JustChattingNavigationBarItem(
                    label = stringResource(xyz.dnieln7.chats.R.string.chats),
                    icon = Icons.Rounded.Chat,
                    destination = ChatsDestination,
                    onClick = { navController.navigateTo(it) },
                    isSelected = { compareRouteToDestination(currentRoute, it) }
                )
                JustChattingNavigationBarItem(
                    label = stringResource(xyz.dnieln7.profile.R.string.profile),
                    icon = Icons.Rounded.AccountCircle,
                    destination = ProfileDestination,
                    onClick = { navController.navigateTo(it) },
                    isSelected = { compareRouteToDestination(currentRoute, it) }
                )
            }
        }
    ) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = FriendshipsDestination.route,
        ) {
            friendshipsNavigation()
            chatsNavigation()
            profileNavigation(navigateToLogin = onLoggedOut)
        }
    }
}

object HomeDestination : NavDestination("just-chatting/home")

@Composable
fun RowScope.JustChattingNavigationBarItem(
    label: String,
    icon: ImageVector,
    destination: NavDestination,
    onClick: (NavDestination) -> Unit,
    isSelected: (NavDestination) -> Boolean,
    modifier: Modifier = Modifier,
) {
    NavigationBarItem(
        modifier = modifier,
        icon = { Icon(imageVector = icon, contentDescription = label) },
        label = { Text(label) },
        selected = isSelected(destination),
        onClick = { onClick(destination) }
    )
}
