package io.github.warahiko.shoppingmemoapp.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoScaffold
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme
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
                Divider(color = MaterialTheme.colors.onBackground)
            }
        }
    }
}

@Composable
fun ShoppingItemRow(shoppingItem: ShoppingItem) {
    Row {
        Checkbox(
            shoppingItem.isDone,
            onCheckedChange = {
                // TODO
            },
            modifier = Modifier.padding(8.dp),
        )
        Text(
            shoppingItem.name,
            textDecoration = if (shoppingItem.isDone) TextDecoration.LineThrough else null,
            color = if (shoppingItem.isDone) Color.Gray else Color.Unspecified,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
        )
        Text(
            shoppingItem.count.toString(),
            textDecoration = if (shoppingItem.isDone) TextDecoration.LineThrough else null,
            color = if (shoppingItem.isDone) Color.Gray else Color.Unspecified,
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Preview
@Composable
private fun ShoppingListPreview() {
    val items = listOf(
        ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1, true),
        ShoppingItem(id = UUID.randomUUID(), name = "たまねぎ", 1, false),
        ShoppingItem(id = UUID.randomUUID(), name = "卵", 1, false),
        ShoppingItem(id = UUID.randomUUID(), name = "牛乳", 3, true),
    )
    ShoppingMemoAppTheme {
        Surface {
            ShoppingList(items)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShoppingListDarkPreview() {
    val items = listOf(
        ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1, true),
        ShoppingItem(id = UUID.randomUUID(), name = "たまねぎ", 1, false),
        ShoppingItem(id = UUID.randomUUID(), name = "卵", 1, false),
        ShoppingItem(id = UUID.randomUUID(), name = "牛乳", 3, true),
    )
    ShoppingMemoAppTheme {
        Surface {
            ShoppingList(items)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShoppingItemRowPreview() {
    val item = ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1, true)
    ShoppingMemoAppTheme {
        ShoppingItemRow(item)
    }
}
