package io.github.warahiko.shoppingmemoapp.ui.tag

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.ui.tag.add.AddTagScreen
import io.github.warahiko.shoppingmemoapp.ui.tag.edit.EditTagScreen
import io.github.warahiko.shoppingmemoapp.ui.tag.list.TagListScreen

@Composable
fun TagScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Tags.route) {
        composable(Screen.Tags.route) {
            TagListScreen(
                onClickAddButton = {
                    navController.navigate(Screen.Add.route)
                },
                onEdit = {
                    navController.navigate(Screen.Edit.actualRoute(it.id.toString()))
                }
            )
        }
        composable(Screen.Add.route) {
            AddTagScreen(
                onBack = { navController.navigateUp() },
            )
        }
        composable(Screen.Edit.route) { backStackEntry ->
            val tagId = backStackEntry.arguments?.getString(Screen.Edit.itemKey)
                ?: run {
                    navController.popBackStack()
                    return@composable
                }
            EditTagScreen(
                defaultTagId = tagId,
                onBack = { navController.popBackStack() },
            )
        }
    }
}

private sealed class Screen(
    val route: String,
) {
    object Tags : Screen("tags")
    object Add : Screen("tags/add")
    object Edit : Screen("tags/edit/{itemId}") {
        const val itemKey = "itemId"
        fun actualRoute(tagId: String): String = "tags/edit/$tagId"
    }
}
