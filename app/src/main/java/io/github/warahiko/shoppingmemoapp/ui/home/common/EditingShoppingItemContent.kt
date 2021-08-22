package io.github.warahiko.shoppingmemoapp.ui.home.common

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.model.ShoppingItemEditable
import io.github.warahiko.shoppingmemoapp.model.Tag
import io.github.warahiko.shoppingmemoapp.preview.getSample
import io.github.warahiko.shoppingmemoapp.ui.common.compositionlocal.LocalTagList
import io.github.warahiko.shoppingmemoapp.ui.theme.ShoppingMemoAppTheme

@Composable
fun EditingShoppingItemContent(
    shoppingItem: ShoppingItemEditable,
    onChangeItem: (ShoppingItemEditable) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TagSelector(
            defaultTag = shoppingItem.tag,
            onChangeTag = {
                onChangeItem(shoppingItem.copy(name = it.name, tag = it))
            },
        )
        TextField(
            value = shoppingItem.count,
            onValueChange = {
                onChangeItem(shoppingItem.copy(count = it))
            },
            label = {
                Text(stringResource(R.string.home_add_dialog_count))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        TextField(
            value = shoppingItem.memo,
            onValueChange = {
                onChangeItem(shoppingItem.copy(memo = it))
            },
            label = {
                Text(stringResource(R.string.home_add_dialog_memo))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
    }
}

@Composable
private fun TagSelector(
    modifier: Modifier = Modifier,
    defaultTag: Tag? = null,
    onChangeTag: (Tag) -> Unit = {},
) {
    val tagList = LocalTagList.current
    var selectedTag by remember { mutableStateOf(defaultTag) }
    val focusManager = LocalFocusManager.current
    var isTagExpanded by remember { mutableStateOf(false) }
    val closeList = {
        isTagExpanded = false
        focusManager.clearFocus()
    }

    BoxWithConstraints(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedTag?.toString() ?: "",
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(stringResource(R.string.home_add_dialog_tag))
                },
                trailingIcon = {
                    Icon(
                        if (isTagExpanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        isTagExpanded = it.isFocused
                    },
            )
            DropdownMenu(
                expanded = isTagExpanded,
                onDismissRequest = closeList,
                modifier = Modifier
                    .width(this@BoxWithConstraints.maxWidth)
                    .heightIn(max = 400.dp),
            ) {
                tagList.groupBy { it.type }
                    .toList()
                    .forEach { (type, list) ->
                        Text(
                            text = type,
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(8.dp),
                        )
                        Divider()
                        list.forEach { tag ->
                            DropdownMenuItem(onClick = {
                                selectedTag = tag
                                onChangeTag(tag)
                                closeList()
                            }) {
                                Text(tag.name)
                            }
                        }
                    }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditingShoppingItemContentPreview() {
    val item = ShoppingItem.getSample()
    ShoppingMemoAppTheme {
        EditingShoppingItemContent(item.toEditable(), {})
    }
}
