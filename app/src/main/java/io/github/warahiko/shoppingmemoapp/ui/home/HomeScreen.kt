package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.ui.common.compositionlocal.LocalTagMap
import io.github.warahiko.shoppingmemoapp.ui.home.add.ShoppingItemAddScreen
import io.github.warahiko.shoppingmemoapp.ui.home.edit.ShoppingItemEditScreen
import io.github.warahiko.shoppingmemoapp.ui.home.list.HomeListScreen

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val tagMap by homeViewModel.tagMapFlow.collectAsState()

    NavHost(navController = navController, startDestination = Screen.ShoppingItems.route) {
        composable(Screen.ShoppingItems.route) {
            HomeListScreen(
                onClickAddButton = { navController.navigate(Screen.Add.route) },
                onEdit = { navController.navigate(Screen.Edit.actualRoute(it.id.toString())) },
            )
        }
        composable(Screen.Add.route) {
            CompositionLocalProvider(LocalTagMap provides tagMap) {
                ShoppingItemAddScreen(
                    onBack = { navController.popBackStack() },
                )
            }
        }
        composable(Screen.Edit.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(Screen.Edit.itemIdKey)
                ?: run {
                    navController.popBackStack()
                    return@composable
                }
            CompositionLocalProvider(LocalTagMap provides tagMap) {
                ShoppingItemEditScreen(
                    defaultShoppingItemId = itemId,
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}

private sealed class Screen(
    val route: String,
) {
    object ShoppingItems : Screen("shopping-items")
    object Add : Screen("shopping-items/add")
    object Edit : Screen("shopping-items/edit/{itemId}") {
        const val itemIdKey = "itemId"
        fun actualRoute(itemId: String): String = "shopping-items/edit/${itemId}"
    }
}
