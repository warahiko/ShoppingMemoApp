package io.github.warahiko.shoppingmemoapp.ui.common

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.warahiko.shoppingmemoapp.R

@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.error_dialog_title))
        },
        text = {
            Text(message)
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.confirm))
            }
        },
        modifier = modifier,
    )
}
