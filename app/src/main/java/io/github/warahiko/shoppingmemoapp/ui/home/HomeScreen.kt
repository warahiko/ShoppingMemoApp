package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoScaffold

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val shoppingItems by homeViewModel.shoppingListFlow.collectAsState()
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()
    val shouldShowAddDialog by homeViewModel.shouldShowAddDialog.collectAsState()
    val itemToEdit by homeViewModel.itemToEdit.collectAsState()
    val shoppingItemToOperate by homeViewModel.shoppingItemToOperate.collectAsState()

    ShoppingMemoScaffold(
        title = stringResource(R.string.app_name),
        appBarIcon = Icons.Default.ShoppingCart,
        floatingActionButton = {
            FloatingActionButton(
                onClick = homeViewModel::showAddDialog,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    ) {
        ShoppingList(
            shoppingItems = shoppingItems,
            isRefreshing = isRefreshing,
            onRefresh = homeViewModel::fetchShoppingList,
            onIsDoneChange = homeViewModel::changeShoppingItemIsDone,
            onLongPressItem = homeViewModel::showOperationDialog,
        )
    }

    if (shouldShowAddDialog) {
        AddDialog(
            onDismiss = homeViewModel::hideAddDialog,
            onAdd = homeViewModel::addShoppingItem,
        )
    }

    itemToEdit?.let {
        EditingDialog(
            defaultShoppingItem = it,
            onDismiss = homeViewModel::hideEditingDialog,
            onConfirm = homeViewModel::editShoppingItem,
        )
    }

    shoppingItemToOperate?.let {
        OperationDialog(
            shoppingItem = it,
            onDismiss = homeViewModel::hideOperationDialog,
            onEdit = { homeViewModel.showEditingDialog(it) },
            onArchive = { homeViewModel.archiveShoppingItem(it) },
            onDelete = { homeViewModel.deleteShoppingItem(it) },
        )
    }

    LaunchedEffect(true) {
        homeViewModel.fetchShoppingList()
    }
}
