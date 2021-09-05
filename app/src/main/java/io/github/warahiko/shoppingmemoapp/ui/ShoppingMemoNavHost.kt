package io.github.warahiko.shoppingmemoapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.ui.splash.SplashScreen

@Composable
fun ShoppingMemoNavHost(
    viewModel: ShoppingMemoViewModel = viewModel(),
) {
    val navController = rememberNavController()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Splash.route) {
            SplashScreen()
        }
        composable(Screen.Contents.route) {
            BottomNavHost(viewModelStoreOwner)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchShoppingItems().join()
        navController.navigate(Screen.Contents.route) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    }
}

private sealed class Screen(
    val route: String,
) {
    object Splash : Screen("splash")
    object Contents : Screen("contents")
}
