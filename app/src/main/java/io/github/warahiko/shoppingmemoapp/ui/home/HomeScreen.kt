package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.ui.common.compositionlocal.LocalTagList
import io.github.warahiko.shoppingmemoapp.ui.home.add.AddScreen
import io.github.warahiko.shoppingmemoapp.ui.home.edit.EditScreen
import io.github.warahiko.shoppingmemoapp.ui.home.list.ListScreen
import java.util.UUID

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    val shoppingItems by homeViewModel.shoppingListFlow.collectAsState()
    val tagList by homeViewModel.tagListFlow.collectAsState()
    val isRefreshing by homeViewModel.isRefreshing.collectAsState()

    NavHost(navController = navController, startDestination = "shopping-list") {
        composable("shopping-list") {
            ListScreen(
                shoppingItems = shoppingItems,
                isRefreshing = isRefreshing,
                onClickAddButton = { navController.navigate("shopping-list/add") },
                onRefresh = homeViewModel::fetchShoppingList,
                onIsDoneChange = homeViewModel::changeShoppingItemIsDone,
                onEdit = { navController.navigate("shopping-list/edit/${it.id}") },
                onArchive = { homeViewModel.archiveShoppingItem(it) },
                onDelete = { homeViewModel.deleteShoppingItem(it) },
            )
        }
        composable("shopping-list/add") {
            CompositionLocalProvider(LocalTagList provides tagList) {
                AddScreen(
                    navController = navController,
                    onAdd = {
                        homeViewModel.addShoppingItem(it)
                        navController.popBackStack()
                    },
                )
            }
        }
        composable("shopping-list/edit/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            val item = shoppingItems.singleOrNull { it.id == UUID.fromString(itemId) } ?: run {
                navController.popBackStack()
                return@composable
            }
            CompositionLocalProvider(LocalTagList provides tagList) {
                EditScreen(
                    navController = navController,
                    defaultShoppingItem = item,
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
