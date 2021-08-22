package io.github.warahiko.shoppingmemoapp.ui.home.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
import androidx.navigation.NavHostController
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItemEditable
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoScaffold
import io.github.warahiko.shoppingmemoapp.ui.home.common.EditingShoppingItemContent
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

@Composable
fun AddScreen(
    navController: NavHostController,
    onAdd: (item: ShoppingItem) -> Unit,
) {
    ShoppingMemoScaffold(
        title = stringResource(R.string.home_add_screen_name),
        appBarIcon = Icons.Default.ArrowBack,
        onClickAppBarIcon = { navController.popBackStack() },
    ) {
        AddScreenContent(onAdd = onAdd)
    }
}

@Composable
private fun AddScreenContent(
    onAdd: (item: ShoppingItem) -> Unit,
) {
    val (shoppingItem, setShoppingItem) = remember { mutableStateOf(ShoppingItemEditable()) }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        EditingShoppingItemContent(
            shoppingItem = shoppingItem,
            onChangeItem = setShoppingItem,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = { onAdd(shoppingItem.fix()) },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End),
        ) {
            Text(stringResource(R.string.home_add_dialog_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddScreenPreview() {
    ShoppingMemoAppTheme {
        AddScreenContent(onAdd = {})
    }
}
