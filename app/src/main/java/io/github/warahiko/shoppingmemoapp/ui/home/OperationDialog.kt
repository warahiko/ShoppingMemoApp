package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.preview.getSample
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

@Composable
fun OperationDialog(
    shoppingItem: ShoppingItem,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onArchive: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(modifier = modifier.shadow(4.dp)) {
            OperationDialogContent(shoppingItem, onDismiss, modifier, onArchive, onDelete)
        }
    }
}

@Composable
private fun OperationDialogContent(
    shoppingItem: ShoppingItem,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    onArchive: () -> Unit = {},
    onDelete: () -> Unit = {},
) {
    val selectionModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    Column(modifier = modifier) {
        Text(
            shoppingItem.name,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondary,
            modifier = Modifier
                .padding(start = 8.dp)
                .padding(vertical = 16.dp),
        )
        Divider(color = MaterialTheme.colors.secondary)
        Text(
            stringResource(R.string.home_operation_dialog_archive),
            modifier = Modifier
                .clickable(onClick = onArchive)
                .then(selectionModifier),
        )
        Divider()
        Text(
            stringResource(R.string.home_operation_dialog_delete),
            modifier = Modifier
                .clickable(onClick = onDelete)
                .then(selectionModifier),
        )
        Divider()
        Text(
            stringResource(R.string.cancel),
            modifier = Modifier
                .clickable(onClick = onDismiss)
                .then(selectionModifier),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OperationDialogContentPreview() {
    ShoppingMemoAppTheme {
        OperationDialogContent(shoppingItem = ShoppingItem.getSample(), {})
    }
}
