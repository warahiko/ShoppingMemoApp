package io.github.warahiko.shoppingmemoapp.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme
import java.util.UUID

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

@Preview
@Composable
private fun ShoppingListPreview() {
    val items = listOf(
        ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1, true, "memo"),
        ShoppingItem(id = UUID.randomUUID(), name = "たまねぎ", 1, false, ""),
        ShoppingItem(id = UUID.randomUUID(), name = "卵", 1, false, "memo"),
        ShoppingItem(id = UUID.randomUUID(), name = "牛乳", 3, true, ""),
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
        ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1, true, "memo"),
        ShoppingItem(id = UUID.randomUUID(), name = "たまねぎ", 1, false, ""),
        ShoppingItem(id = UUID.randomUUID(), name = "卵", 1, false, "memo"),
        ShoppingItem(id = UUID.randomUUID(), name = "牛乳", 3, true, ""),
    )
    ShoppingMemoAppTheme {
        Surface {
            ShoppingList(items)
        }
    }
}
