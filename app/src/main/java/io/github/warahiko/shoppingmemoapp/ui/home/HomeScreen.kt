package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.ui.common.compositionlocal.LocalTagMap
import io.github.warahiko.shoppingmemoapp.ui.home.add.AddScreen
import io.github.warahiko.shoppingmemoapp.ui.home.edit.EditScreen
import io.github.warahiko.shoppingmemoapp.ui.home.list.ListScreen
import java.util.UUID

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val shoppingItems by homeViewModel.shoppingListFlow.collectAsState()
    val tagMap by homeViewModel.tagMapFlow.collectAsState()
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()

    NavHost(navController = navController, startDestination = Screen.ShoppingItems.route) {
        composable(Screen.ShoppingItems.route) {
            ListScreen(
                shoppingItems = shoppingItems,
                isRefreshing = isRefreshing,
                onClickAddButton = { navController.navigate(Screen.Add.route) },
                onRefresh = homeViewModel::fetchShoppingList,
                onClickItemRow = homeViewModel::changeShoppingItemIsDone,
                onEdit = { navController.navigate(Screen.Edit.actualRoute(it.id.toString())) },
                onArchive = { homeViewModel.archiveShoppingItem(it) },
                onDelete = { homeViewModel.deleteShoppingItem(it) },
            )
        }
        composable(Screen.Add.route) {
            CompositionLocalProvider(LocalTagMap provides tagMap) {
                AddScreen(
                    onBack = { navController.popBackStack() },
                    onAdd = {
                        homeViewModel.addShoppingItem(it)
                        navController.popBackStack()
                    },
                )
            }
        }
        composable(Screen.Edit.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(Screen.Edit.itemIdKey)
            val item = shoppingItems.singleOrNull { it.id == UUID.fromString(itemId) } ?: run {
                navController.popBackStack()
                return@composable
            }
            CompositionLocalProvider(LocalTagMap provides tagMap) {
                EditScreen(
                    defaultShoppingItem = item,
                    onBack = { navController.popBackStack() },
                    onConfirm = {
                        homeViewModel.editShoppingItem(it)
                        navController.popBackStack()
                    },
                )
            }
        }
    }

    LaunchedEffect(true) {
        homeViewModel.fetchShoppingList()
    }
}

sealed class Screen(
    val route: String,
) {
    object ShoppingItems : Screen("shopping-items")
    object Add : Screen("shopping-items/add")
    object Edit : Screen("shopping-items/edit/{itemId}") {
        const val itemIdKey = "itemId"
        fun actualRoute(itemId: String): String = "shopping-items/edit/${itemId}"
    }
}
