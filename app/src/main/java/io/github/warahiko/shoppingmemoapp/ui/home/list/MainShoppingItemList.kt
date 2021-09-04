package io.github.warahiko.shoppingmemoapp.ui.home.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.preview.getSampleList
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

@Composable
fun MainShoppingItemList(
    shoppingItems: List<ShoppingItem>,
    modifier: Modifier = Modifier,
    onClickItemRow: (item: ShoppingItem) -> Unit = {},
    onEdit: (item: ShoppingItem) -> Unit = {},
    onArchive: (item: ShoppingItem) -> Unit = {},
    onDelete: (item: ShoppingItem) -> Unit = {},
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
                onClickItemRow = onClickItemRow,
                onEdit = onEdit,
                onArchive = onArchive,
                onDelete = onDelete,
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
    onClickItemRow: (item: ShoppingItem) -> Unit = {},
    onEdit: (item: ShoppingItem) -> Unit = {},
    onArchive: (item: ShoppingItem) -> Unit = {},
    onDelete: (item: ShoppingItem) -> Unit = {},
) {
    var showOperation by remember { mutableStateOf(false) }
    var dropdownOffset by remember { mutableStateOf(Offset.Zero) }

    Box {
        ShoppingItemRow(
            shoppingItem = item,
            onClick = { onClickItemRow(item) },
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
            DropdownMenuItem(onClick = { onEdit(item) }) {
                Text(stringResource(R.string.home_operation_dialog_edit))
            }
            DropdownMenuItem(onClick = { onArchive(item) }) {
                Text(stringResource(R.string.home_operation_dialog_archive))
            }
            DropdownMenuItem(onClick = { onDelete(item) }) {
                Text(stringResource(R.string.home_operation_dialog_delete))
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
private fun ShoppingListPreview() {
    val items = ShoppingItem.getSampleList()
    ShoppingMemoAppTheme {
        Surface {
            MainShoppingItemList(items)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShoppingListDarkPreview() {
    val items = ShoppingItem.getSampleList()
    ShoppingMemoAppTheme {
        Surface {
            MainShoppingItemList(items)
        }
    }
}
