package io.github.warahiko.shoppingmemoapp.ui.tag

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.ui.tag.list.ListScreen

@Composable
fun TagScreen(
    tagViewModel: TagViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val tags by tagViewModel.tags.collectAsState()
    val isRefreshing by tagViewModel.isRefreshing.collectAsState()

    NavHost(navController = navController, startDestination = "tag-list") {
        composable("tag-list") {
            ListScreen(
                tags = tags,
                isRefreshing = isRefreshing,
            )
        }
    }
}
