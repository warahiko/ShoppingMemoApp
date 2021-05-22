package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme
import java.util.UUID

@Composable
fun MemoDialog(
    shoppingItem: ShoppingItem,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(shoppingItem.name)
        },
        text = {
            Text(shoppingItem.memo)
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("確認")
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun MemoDialogPreview() {
    val item = ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1, true, "memo")
    ShoppingMemoAppTheme {
        Surface {
            MemoDialog(item, {})
        }
    }
}
