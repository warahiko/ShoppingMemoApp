package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoScaffold
import java.util.UUID

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

@Composable
fun ShoppingList(shoppingItems: List<ShoppingItem>) {
    LazyColumn(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        items(shoppingItems.size, key = { shoppingItems[it].id }) { index ->
            val item = shoppingItems[index]
            ShoppingItemRow(item)
            if (index < shoppingItems.size - 1) {
                Divider(color = Color.Black)
            }
        }
    }
}

@Composable
fun ShoppingItemRow(shoppingItem: ShoppingItem) {
    Row {
        Text(
            shoppingItem.name,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
        )
        Text(
            shoppingItem.count.toString(),
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Preview
@Composable
private fun ShoppingListPreview() {
    val items = listOf(
        ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1),
        ShoppingItem(id = UUID.randomUUID(), name = "たまねぎ", 1),
        ShoppingItem(id = UUID.randomUUID(), name = "卵", 1),
        ShoppingItem(id = UUID.randomUUID(), name = "牛乳", 3),
    )
    ShoppingList(items)
}

@Preview
@Composable
private fun ShoppingItemRowPreview() {
    val item = ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1)
    ShoppingItemRow(item)
}
