package io.github.warahiko.shoppingmemoapp.ui.tag.common

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
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
import androidx.compose.ui.unit.dp
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.ui.common.compositionlocal.LocalTypeList

@Composable
fun EditingTagContent(
    tag: Tag,
    modifier: Modifier = Modifier,
    onChangeTag: (Tag) -> Unit = {},
) {
    Column(modifier = modifier) {
        TypeSelector(
            selectedType = tag.type,
            onChangeType = {
                onChangeTag(tag.copy(type = it))
            },
        )
        TextField(
            value = tag.name,
            onValueChange = {
                onChangeTag(tag.copy(name = it))
            },
            label = {
                Text(stringResource(R.string.tag_add_name_label))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
    }
}

@Composable
private fun TypeSelector(
    modifier: Modifier = Modifier,
    selectedType: String? = null,
    onChangeType: (String) -> Unit = {},
) {
    val types: List<String> = LocalTypeList.current
    val focusManager = LocalFocusManager.current
    var isTypeExpanded by remember { mutableStateOf(false) }
    val closeList = {
        isTypeExpanded = false
        focusManager.clearFocus()
    }

    BoxWithConstraints(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedType ?: "",
                onValueChange = {},
                readOnly = true,
                label = {
                    Text(stringResource(R.string.tag_add_type_label))
                },
                trailingIcon = {
                    Icon(
                        if (isTypeExpanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        isTypeExpanded = it.isFocused
                    },
            )
            DropdownMenu(
                expanded = isTypeExpanded,
                onDismissRequest = closeList,
                modifier = Modifier
                    .width(this@BoxWithConstraints.maxWidth)
                    .heightIn(max = 400.dp),
            ) {
                types.forEach { type ->
                    DropdownMenuItem(onClick = {
                        onChangeType(type)
                        closeList()
                    }) {
                        Text(text = type)
                    }
                }
            }
        }
    }
}
