package io.github.warahiko.shoppingmemoapp.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.ui.home.HomeScreen
import io.github.warahiko.shoppingmemoapp.ui.tag.TagScreen

@Composable
fun ShoppingMemoNavHost() {
    val navController = rememberNavController()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    NavHost(navController = navController, startDestination = "shopping-items") {
        composable("shopping-items") {
            HomeScreen(viewModel(viewModelStoreOwner))
        }
        composable("tags") {
            TagScreen(viewModel(viewModelStoreOwner))
        }
    }
}
