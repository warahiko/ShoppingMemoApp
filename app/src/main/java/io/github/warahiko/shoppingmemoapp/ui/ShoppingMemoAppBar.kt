package io.github.warahiko.shoppingmemoapp.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShoppingMemoAppBar(
    title: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    TopAppBar(modifier = modifier) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.width(8.dp))
        } else {
            Spacer(modifier = Modifier.padding(16.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
        )
    }
}

@Preview
@Composable
private fun ShoppingMemoAppBarPreview() {
    ShoppingMemoAppBar(title = "買い物メモ", icon = Icons.Default.ShoppingCart)
}

@Preview
@Composable
private fun ShoppingMemoAppBar_IconEmptyPreview() {
    ShoppingMemoAppBar(title = "買い物メモ")
}
