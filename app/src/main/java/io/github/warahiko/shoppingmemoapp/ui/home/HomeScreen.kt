package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoScaffold

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val shoppingItems by homeViewModel.shoppingListFlow.collectAsState()
    ShoppingMemoScaffold(
        title = stringResource(R.string.app_name),
        appBarIcon = Icons.Default.ShoppingCart,
    ) {
        ShoppingList(shoppingItems)
    }

    LaunchedEffect(true) {
        homeViewModel.fetchShoppingList()
    }
}
