package io.github.warahiko.shoppingmemoapp.ui.tag.add

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
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoAppBar
import io.github.warahiko.shoppingmemoapp.ui.tag.common.EditingTagContent

@Composable
fun AddScreen(
    onBack: () -> Unit,
    onAdd: (tag: Tag) -> Unit,
) {
    Scaffold(
        topBar = {
            ShoppingMemoAppBar(
                title = stringResource(R.string.tag_add_title),
                icon = Icons.Default.ArrowBack,
                onClickIcon = onBack,
            )
        },
    ) {
        AddScreenContent(onAdd = onAdd)
    }
}

@Composable
private fun AddScreenContent(
    onAdd: (tag: Tag) -> Unit,
) {
    val (tag, setTag) = remember { mutableStateOf(Tag.newInstance()) }
    val buttonEnabled = tag.name.isNotBlank() && tag.type.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        EditingTagContent(
            tag = tag,
            onChangeTag = setTag,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(
            onClick = { onAdd(tag) },
            enabled = buttonEnabled,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End),
        ) {
            Text(stringResource(R.string.tag_add_button))
        }
    }
}
