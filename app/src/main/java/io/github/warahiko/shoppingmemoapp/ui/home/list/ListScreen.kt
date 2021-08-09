package io.github.warahiko.shoppingmemoapp.ui.home.list

import androidx.compose.runtime.Composable
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem

@Composable
fun ListScreen(
    shoppingItems: List<ShoppingItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onIsDoneChange: (item: ShoppingItem, newIsDone: Boolean) -> Unit,
    onLongPressItem: (item: ShoppingItem) -> Unit,
) {
    ShoppingList(
        shoppingItems = shoppingItems,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        onIsDoneChange = onIsDoneChange,
        onLongPressItem = onLongPressItem,
    )
}
