package io.github.warahiko.shoppingmemoapp.ui.home.list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.preview.getSampleMap
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

@Composable
fun ArchivedShoppingItemList(
    shoppingItems: Map<String, List<ShoppingItem>>,
    modifier: Modifier = Modifier,
    onDelete: (item: ShoppingItem) -> Unit = {},
) {
    if (shoppingItems.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                stringResource(id = R.string.home_list_empty),
                modifier = Modifier.align(Alignment.Center),
            )
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        shoppingItems.forEach { (date, items) ->
            stickyHeader {
                Box(
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.background)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        date,
                        style = MaterialTheme.typography.h6,
                    )
                }
            }
            itemsIndexed(items, key = { _, item -> item.id }) { index, item ->
                ItemRow(
                    item = item,
                    onDelete = onDelete,
                )
                if (index < items.size - 1) {
                    Divider(color = MaterialTheme.colors.onBackground)
                }
            }
        }
    }
}

@Composable
private fun ItemRow(
    item: ShoppingItem,
    onDelete: (item: ShoppingItem) -> Unit = {},
) {
    var showOperation by remember { mutableStateOf(false) }
    var dropdownOffset by remember { mutableStateOf(Offset.Zero) }

    Box {
        ShoppingItemRow(
            shoppingItem = item,
            checkBoxIsVisible = false,
            onLongPress = { offset ->
                showOperation = true
                dropdownOffset = offset
            },
        )
        DropdownMenu(
            expanded = showOperation,
            onDismissRequest = { showOperation = false },
            offset = LocalDensity.current.run {
                DpOffset(dropdownOffset.x.toDp(), 0.dp)
            },
        ) {
            DropdownMenuItem(onClick = { onDelete(item) }) {
                Text(stringResource(R.string.home_operation_delete))
            }
            Divider()
            DropdownMenuItem(onClick = { showOperation = false }) {
                Text(stringResource(R.string.cancel))
            }
        }
    }
}

@Preview
@Composable
private fun ArchivedShoppingItemListPreview() {
    val items = ShoppingItem.getSampleMap()
    ShoppingMemoAppTheme {
        Surface {
            ArchivedShoppingItemList(items)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ArchivedShoppingItemListDarkPreview() {
    val items = ShoppingItem.getSampleMap()
    ShoppingMemoAppTheme {
        Surface {
            ArchivedShoppingItemList(items)
        }
    }
}
