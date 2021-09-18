package io.github.warahiko.shoppingmemoapp.ui.tag

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.ui.common.compositionlocal.LocalTypeList
import io.github.warahiko.shoppingmemoapp.ui.tag.add.AddScreen
import io.github.warahiko.shoppingmemoapp.ui.tag.list.TagListScreen

@Composable
fun TagScreen(
    tagViewModel: TagViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val types by tagViewModel.types.collectAsState()

    NavHost(navController = navController, startDestination = Screen.Tags.route) {
        composable(Screen.Tags.route) {
            TagListScreen(
                onClickAddButton = {
                    navController.navigate(Screen.Add.route)
                },
            )
        }
        composable(Screen.Add.route) {
            CompositionLocalProvider(LocalTypeList provides types) {
                AddScreen(
                    onBack = { navController.navigateUp() },
                    onAdd = tagViewModel::addTag,
                )
            }
        }
    }
}

private sealed class Screen(
    val route: String,
) {
    object Tags : Screen("tags")
    object Add : Screen("tags/add")
}
