package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem

@Composable
fun AddDialog(
    onDismiss: () -> Unit,
    onAdd: (ShoppingItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (shoppingItem, setShoppingItem) = remember { mutableStateOf(ShoppingItem()) }
    Dialog(onDismissRequest = onDismiss) {
        Surface {
            Column(modifier = modifier) {
                EditingShoppingItemContent(
                    shoppingItem = shoppingItem,
                    onChangeItem = setShoppingItem,
                )
                Button(
                    onClick = { onAdd(shoppingItem) },
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.End),
                ) {
                    Text(stringResource(R.string.home_add_dialog_button))
                }
            }
        }
    }
}
