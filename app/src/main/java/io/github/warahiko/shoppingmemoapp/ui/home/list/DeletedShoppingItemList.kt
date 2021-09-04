package io.github.warahiko.shoppingmemoapp.ui.home.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
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
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.preview.getSampleList
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

@Composable
fun DeletedShoppingItemList(
    shoppingItems: List<ShoppingItem>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        items(shoppingItems.size, key = { shoppingItems[it].id }) { index ->
            val item = shoppingItems[index]
            ItemRow(
                item = item,
            )
            if (index < shoppingItems.size - 1) {
                Divider(color = MaterialTheme.colors.onBackground)
            }
        }
    }
}

@Composable
private fun ItemRow(
    item: ShoppingItem,
) {
    Box {
        ShoppingItemRow(
            shoppingItem = item,
            checkBoxIsVisible = false,
        )
    }
}

@Preview
@Composable
private fun DeletedShoppingItemListPreview() {
    val items = ShoppingItem.getSampleList()
    ShoppingMemoAppTheme {
        Surface {
            DeletedShoppingItemList(items)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DeletedShoppingItemListDarkPreview() {
    val items = ShoppingItem.getSampleList()
    ShoppingMemoAppTheme {
        Surface {
            DeletedShoppingItemList(items)
        }
    }
}
