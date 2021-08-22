package io.github.warahiko.shoppingmemoapp.ui.home.list

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.preview.getSample
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

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
                Text(stringResource(R.string.home_memo_dialog_button))
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
private fun MemoDialogPreview() {
    val item = ShoppingItem.getSample()
    ShoppingMemoAppTheme {
        Surface {
            MemoDialog(item, {})
        }
    }
}
