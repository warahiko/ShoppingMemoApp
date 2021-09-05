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
import androidx.lifecycle.ViewModelStoreOwner
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
fun BottomNavHost(
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            BottomNavigation {
                BottomNavScreen.items.forEach { screen ->
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
            startDestination = BottomNavScreen.ShoppingItems.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(BottomNavScreen.ShoppingItems.route) {
                HomeScreen(viewModel(viewModelStoreOwner))
            }
            composable(BottomNavScreen.Tags.route) {
                TagScreen(viewModel(viewModelStoreOwner))
            }
        }
    }
}

private sealed class BottomNavScreen(
    val route: String,
    @StringRes val navigationTextResourceId: Int,
    val navigationIcon: ImageVector,
) {
    object ShoppingItems : BottomNavScreen(
        "shopping-items",
        R.string.bottom_navigation_shopping_items,
        Icons.Default.ShoppingCart,
    )

    object Tags : BottomNavScreen(
        "tags",
        R.string.bottom_navigation_tags,
        Icons.Default.Label,
    )

    companion object {
        val items: List<BottomNavScreen> = listOf(
            ShoppingItems,
            Tags,
        )
    }
}
