package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme
import java.util.UUID

@Composable
fun ShoppingItemRow(shoppingItem: ShoppingItem) {
    Row {
        Checkbox(
            shoppingItem.isDone,
            onCheckedChange = {
                // TODO
            },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
        )
        Text(
            shoppingItem.name,
            textDecoration = if (shoppingItem.isDone) TextDecoration.LineThrough else null,
            color = if (shoppingItem.isDone) Color.Gray else Color.Unspecified,
            modifier = Modifier
                .padding(8.dp)
                .weight(1f)
                .align(Alignment.CenterVertically),
        )
        if (shoppingItem.memo.isNotBlank()) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        // TODO
                    }
                    .align(Alignment.CenterVertically)
            )
        }
        Text(
            shoppingItem.count.toString(),
            textDecoration = if (shoppingItem.isDone) TextDecoration.LineThrough else null,
            color = if (shoppingItem.isDone) Color.Gray else Color.Unspecified,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ShoppingItemRowPreview() {
    val item = ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1, true, "memo")
    ShoppingMemoAppTheme {
        ShoppingItemRow(item)
    }
}