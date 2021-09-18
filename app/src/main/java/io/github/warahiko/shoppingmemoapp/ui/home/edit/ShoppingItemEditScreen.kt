package io.github.warahiko.shoppingmemoapp.ui.home.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoAppBar
import io.github.warahiko.shoppingmemoapp.ui.home.common.EditShoppingItemContent
import io.github.warahiko.shoppingmemoapp.ui.preview.getSample
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

@Composable
fun ShoppingItemEditScreen(
    defaultShoppingItemId: String,
    onBack: () -> Unit,
    viewModel: ShoppingItemEditScreenViewModel = hiltViewModel(),
) {
    val defaultShoppingItem = remember {
        viewModel.getShoppingItem(defaultShoppingItemId) ?: run {
            onBack()
            return
        }
    }

    Scaffold(
        topBar = {
            ShoppingMemoAppBar(
                title = stringResource(R.string.home_edit_title),
                icon = Icons.Default.ArrowBack,
                onClickIcon = onBack,
            )
        },
    ) {
        ShoppingItemEditScreenContent(
            defaultShoppingItem = defaultShoppingItem,
            onConfirm = {
                viewModel.editShoppingItem(it)
                onBack()
            },
        )
    }
}

@Composable
private fun ShoppingItemEditScreenContent(
    defaultShoppingItem: ShoppingItem,
    onConfirm: (item: ShoppingItem) -> Unit,
) {
    val (shoppingItem, setShoppingItem) = remember(defaultShoppingItem) {
        mutableStateOf(defaultShoppingItem.toEditable())
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        EditShoppingItemContent(
            shoppingItem = shoppingItem,
            onChangeItem = setShoppingItem,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = { onConfirm(shoppingItem.fix()) },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End),
        ) {
            Text(stringResource(R.string.home_edit_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingItemEditScreenPreview() {
    ShoppingMemoAppTheme {
        ShoppingItemEditScreenContent(defaultShoppingItem = ShoppingItem.getSample(),
            onConfirm = {})
    }
}
