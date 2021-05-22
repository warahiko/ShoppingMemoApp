package io.github.warahiko.shoppingmemoapp.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

@Composable
fun ShoppingMemoScaffold(
    modifier: Modifier = Modifier,
    title: String = "",
    appBarIcon: ImageVector? = null,
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    ShoppingMemoAppTheme {
        Scaffold(
            modifier = modifier,
            topBar = {
                ShoppingMemoAppBar(title = title, icon = appBarIcon)
            },
            floatingActionButton = floatingActionButton,
            content = content,
        )
    }
}

@Preview
@Composable
private fun ShoppingMemoScaffoldPreview() {
    ShoppingMemoScaffoldPreviewImpl()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShoppingMemoScaffoldDarkPreview() {
    ShoppingMemoScaffoldPreviewImpl()
}

@Composable
private fun ShoppingMemoScaffoldPreviewImpl() {
    ShoppingMemoScaffold(
        title = "買い物メモ",
        appBarIcon = Icons.Default.ShoppingCart,
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    ) {
        Text(text = "Hello shopping!")
    }
}
