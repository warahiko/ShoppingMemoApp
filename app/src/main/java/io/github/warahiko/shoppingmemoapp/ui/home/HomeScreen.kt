package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.ui.home.add.AddShoppingItemScreen
import io.github.warahiko.shoppingmemoapp.ui.home.edit.ShoppingItemEditScreen
import io.github.warahiko.shoppingmemoapp.ui.home.list.HomeListScreen

@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.ShoppingItems.route) {
        composable(Screen.ShoppingItems.route) {
            HomeListScreen(
                onClickAddButton = { navController.navigate(Screen.Add.route) },
                onEdit = { navController.navigate(Screen.Edit.actualRoute(it.id.toString())) },
            )
        }
        composable(Screen.Add.route) {
            AddShoppingItemScreen(
                onBack = { navController.popBackStack() },
            )
        }
        composable(Screen.Edit.route) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(Screen.Edit.itemIdKey)
                ?: run {
                    navController.popBackStack()
                    return@composable
                }
            ShoppingItemEditScreen(
                defaultShoppingItemId = itemId,
                onBack = { navController.popBackStack() },
            )
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
