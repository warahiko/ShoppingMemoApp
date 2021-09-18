package io.github.warahiko.shoppingmemoapp.ui.tag

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.ui.tag.add.TagAddScreen
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
            )
        }
        composable(Screen.Add.route) {
            TagAddScreen(
                onBack = { navController.navigateUp() },
            )
        }
    }
}

private sealed class Screen(
    val route: String,
) {
    object Tags : Screen("tags")
    object Add : Screen("tags/add")
}
