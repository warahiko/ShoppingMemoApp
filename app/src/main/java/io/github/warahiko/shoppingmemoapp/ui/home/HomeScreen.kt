package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoScaffold

@Composable
fun HomeScreen() {
    ShoppingMemoScaffold(
        title = stringResource(R.string.app_name),
        appBarIcon = Icons.Default.ShoppingCart,
    ) {}
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
