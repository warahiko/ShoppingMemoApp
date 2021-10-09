package io.github.warahiko.shoppingmemoapp.ui.home.list

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.github.warahiko.shoppingmemoapp.R

@Composable
fun DeleteCompletelyDialog(
    showDialog: Boolean,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    if (!showDialog) return

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.home_list_delete_completely_dialog_title))
        },
        text = {
            Text(stringResource(R.string.home_list_delete_completely_dialog_content))
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.home_list_delete_completely_dialog_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        },
    )
}
