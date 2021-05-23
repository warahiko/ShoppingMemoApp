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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoScaffold

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val shoppingItems by homeViewModel.shoppingListFlow.collectAsState()
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    ShoppingMemoScaffold(
        title = stringResource(R.string.app_name),
        appBarIcon = Icons.Default.ShoppingCart,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddDialog = true
                },
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
            onRefresh = {
                homeViewModel.fetchShoppingList()
            },
            onIsDoneChange = { newShoppingItem ->
                homeViewModel.updateShoppingItem(newShoppingItem)
            }
        )
    }

    if (showAddDialog) {
        AddDialog(
            onDismiss = { showAddDialog = false },
            onAdd = {
                homeViewModel.addShoppingItem(it)
                showAddDialog = false
            }
        )
    }

    LaunchedEffect(true) {
        homeViewModel.fetchShoppingList()
    }
}
