package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme
import java.util.UUID

@Composable
fun AddDialog(
    onDismiss: () -> Unit,
    onAdd: (ShoppingItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (shoppingItem, setShoppingItem) = remember {
        mutableStateOf(ShoppingItem(
            UUID.randomUUID(),
            "",
            1,
            false,
            "",
        ))
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.home_add_dialog_title))
        },
        text = {
            AddDialogContent(shoppingItem = shoppingItem, onChangeItem = setShoppingItem)
        },
        confirmButton = {
            onAdd(shoppingItem)
        },
        modifier = modifier,
    )
}

@Composable
fun AddDialogContent(
    shoppingItem: ShoppingItem,
    onChangeItem: (ShoppingItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TextField(
            value = shoppingItem.name,
            onValueChange = {
                onChangeItem(shoppingItem.copy(name = it))
            },
            label = {
                Text(stringResource(R.string.home_add_dialog_name))
            },
            singleLine = true,
            modifier = Modifier.padding(8.dp),
        )
        TextField(
            value = shoppingItem.count.toString(),
            onValueChange = {
                onChangeItem(shoppingItem.copy(count = it.toInt()))
            },
            label = {
                Text(stringResource(R.string.home_add_dialog_count))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.padding(8.dp),
        )
        TextField(
            value = shoppingItem.memo,
            onValueChange = {
                onChangeItem(shoppingItem.copy(memo = it))
            },
            label = {
                Text(stringResource(R.string.home_add_dialog_memo))
            },
            modifier = Modifier.padding(8.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddDialogContentPreview() {
    val item = ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1, true, "memo")
    ShoppingMemoAppTheme {
        AddDialogContent(item, {})
    }
}
