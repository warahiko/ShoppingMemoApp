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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoScaffold
import io.github.warahiko.shoppingmemoapp.ui.home.add.AddScreen
import io.github.warahiko.shoppingmemoapp.ui.home.edit.EditScreen
import io.github.warahiko.shoppingmemoapp.ui.home.list.ListScreen
import java.util.UUID

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    val shoppingItems by homeViewModel.shoppingListFlow.collectAsState()
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()
    val shoppingItemToOperate by homeViewModel.shoppingItemToOperate.collectAsState()

    ShoppingMemoScaffold(
        title = stringResource(R.string.app_name),
        appBarIcon = Icons.Default.ShoppingCart,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("shopping-list/add") },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "shopping-list") {
            composable("shopping-list") {
                ListScreen(
                    shoppingItems = shoppingItems,
                    isRefreshing = isRefreshing,
                    onRefresh = homeViewModel::fetchShoppingList,
                    onIsDoneChange = homeViewModel::changeShoppingItemIsDone,
                    onLongPressItem = homeViewModel::showOperationDialog,
                )
            }
            composable("shopping-list/add") {
                AddScreen(onAdd = {
                    homeViewModel.addShoppingItem(it)
                    navController.popBackStack()
                })
            }
            composable("shopping-list/edit/{itemId}") { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId")
                val item = shoppingItems.singleOrNull { it.id == UUID.fromString(itemId) } ?: run {
                    navController.popBackStack()
                    return@composable
                }
                EditScreen(
                    defaultShoppingItem = item,
                    onConfirm = {
                        homeViewModel.editShoppingItem(it)
                        navController.popBackStack()
                    },
                )
            }
        }
    }

    shoppingItemToOperate?.let {
        OperationDialog(
            shoppingItem = it,
            onDismiss = homeViewModel::hideOperationDialog,
            onEdit = {
                navController.navigate("shopping-list/edit/${it.id}")
                homeViewModel.hideOperationDialog()
            },
            onArchive = { homeViewModel.archiveShoppingItem(it) },
            onDelete = { homeViewModel.deleteShoppingItem(it) },
        )
    }

    LaunchedEffect(true) {
        homeViewModel.fetchShoppingList()
    }
}
