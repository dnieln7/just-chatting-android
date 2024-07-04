package xyz.dnieln7.friendships.screen.friendshipsearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.PersonSearch
import androidx.compose.material.icons.rounded.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import xyz.dnieln7.composable.alert.AlertAction
import xyz.dnieln7.composable.alert.JCAlert
import xyz.dnieln7.composable.alert.JCErrorAlert
import xyz.dnieln7.composable.button.JCButton
import xyz.dnieln7.composable.progress.JCProgressIndicator
import xyz.dnieln7.composable.spacer.VerticalSpacer
import xyz.dnieln7.composable.textfield.JCOutlinedSearchTextField
import xyz.dnieln7.friendships.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendshipSearchBottomSheet(
    uiState: FriendshipSearchState,
    sheetState: SheetState,
    onAction: (FriendshipSearchAction) -> Unit,
) {
    ModalBottomSheet(
        modifier = Modifier,
        sheetState = sheetState,
        onDismissRequest = { onAction(FriendshipSearchAction.OnDismissClick) },
    ) {
        JCOutlinedSearchTextField(
            modifier = Modifier.padding(horizontal = 12.dp),
            placeholder = stringResource(R.string.search_by_email),
            onSearch = { onAction(FriendshipSearchAction.OnSearchClick(it)) }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 40.dp,
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (uiState) {
                FriendshipSearchState.None -> JCAlert(
                    icon = Icons.Rounded.PersonSearch,
                    text = stringResource(R.string.search_user_hint)
                )

                FriendshipSearchState.Loading -> JCProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )

                is FriendshipSearchState.GetUserError -> {
                    JCErrorAlert(
                        icon = Icons.Rounded.SearchOff,
                        error = uiState.message,
                    )
                }

                is FriendshipSearchState.UserFound -> {
                    Text(text = uiState.user.username, style = MaterialTheme.typography.titleLarge)
                    VerticalSpacer(of = 4.dp)
                    Text(text = uiState.user.email, style = MaterialTheme.typography.bodyLarge)
                    VerticalSpacer(of = 12.dp)
                    JCButton(
                        text = stringResource(R.string.send_friendship_request),
                        onClick = {
                            onAction(FriendshipSearchAction.OnSendFriendshipClick(uiState.user))
                        },
                    )
                }

                is FriendshipSearchState.SendFriendshipSearchRequestError -> JCErrorAlert(
                    icon = Icons.Rounded.Error,
                    error = uiState.message,
                    alertAction = AlertAction(
                        text = stringResource(R.string.try_again),
                        onClick = {
                            onAction(FriendshipSearchAction.OnSendFriendshipClick(uiState.user))
                        },
                    ),
                )

                is FriendshipSearchState.FriendshipRequestSentSearch -> JCAlert(
                    icon = Icons.Rounded.Check,
                    text = stringResource(
                        R.string.friendship_request_sent,
                        uiState.user.username,
                    )
                )
            }
        }
    }
}
