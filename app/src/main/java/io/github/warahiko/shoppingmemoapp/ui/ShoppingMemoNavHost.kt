package io.github.warahiko.shoppingmemoapp.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.ui.home.HomeScreen
import io.github.warahiko.shoppingmemoapp.ui.tag.TagScreen

@Composable
fun ShoppingMemoNavHost() {
    val navController = rememberNavController()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                Screen.items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.navigationIcon, contentDescription = null) },
                        label = { Text(stringResource(screen.navigationTextResourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.ShoppingItems.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(Screen.ShoppingItems.route) {
                HomeScreen(viewModel(viewModelStoreOwner))
            }
            composable(Screen.Tags.route) {
                TagScreen(viewModel(viewModelStoreOwner))
            }
        }
    }
}

sealed class Screen(
    val route: String,
    @StringRes val navigationTextResourceId: Int,
    val navigationIcon: ImageVector,
) {
    object ShoppingItems : Screen(
        "shopping-items",
        R.string.bottom_navigation_shopping_items,
        Icons.Default.ShoppingCart,
    )

    object Tags : Screen(
        "tags",
        R.string.bottom_navigation_tags,
        Icons.Default.Label,
    )

    companion object {
        val items: List<Screen> = listOf(
            ShoppingItems,
            Tags,
        )
    }
}
