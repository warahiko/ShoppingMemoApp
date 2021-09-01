package io.github.warahiko.shoppingmemoapp.ui.home.list

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoScaffold

@Composable
fun ListScreen(
    shoppingItems: List<ShoppingItem>,
    isRefreshing: Boolean,
    onClickAddButton: () -> Unit,
    onRefresh: () -> Unit,
    onIsDoneChange: (item: ShoppingItem, newIsDone: Boolean) -> Unit,
    onEdit: (item: ShoppingItem) -> Unit,
    onArchive: (item: ShoppingItem) -> Unit,
    onDelete: (item: ShoppingItem) -> Unit,
) {
    ShoppingMemoScaffold(
        title = stringResource(R.string.app_name),
        appBarIcon = Icons.Default.ShoppingCart,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClickAddButton,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    ) {
        ListScreenContent(
            shoppingItems = shoppingItems,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            onIsDoneChange = onIsDoneChange,
            onEdit = onEdit,
            onArchive = onArchive,
            onDelete = onDelete,
        )
    }
}

@Composable
private fun ListScreenContent(
    shoppingItems: List<ShoppingItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onIsDoneChange: (item: ShoppingItem, newIsDone: Boolean) -> Unit,
    onEdit: (item: ShoppingItem) -> Unit,
    onArchive: (item: ShoppingItem) -> Unit,
    onDelete: (item: ShoppingItem) -> Unit,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = onRefresh,
    ) {
        MainShoppingItemList(
            shoppingItems = shoppingItems,
            onIsDoneChange = onIsDoneChange,
            onEdit = onEdit,
            onArchive = onArchive,
            onDelete = onDelete,
        )
    }
}
