package io.github.warahiko.shoppingmemoapp.ui.home.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.model.ShoppingItemEditable
import io.github.warahiko.shoppingmemoapp.preview.getSample
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

@Composable
fun EditingShoppingItemContent(
    shoppingItem: ShoppingItemEditable,
    onChangeItem: (ShoppingItemEditable) -> Unit,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        TextField(
            value = shoppingItem.count,
            onValueChange = {
                onChangeItem(shoppingItem.copy(count = it))
            },
            label = {
                Text(stringResource(R.string.home_add_dialog_count))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        TextField(
            value = shoppingItem.memo,
            onValueChange = {
                onChangeItem(shoppingItem.copy(memo = it))
            },
            label = {
                Text(stringResource(R.string.home_add_dialog_memo))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditingShoppingItemContentPreview() {
    val item = ShoppingItem.getSample()
    ShoppingMemoAppTheme {
        EditingShoppingItemContent(item.toEditable(), {})
    }
}
