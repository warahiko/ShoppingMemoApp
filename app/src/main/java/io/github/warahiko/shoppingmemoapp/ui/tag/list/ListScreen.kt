package io.github.warahiko.shoppingmemoapp.ui.tag.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Label
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoAppBar

@Composable
fun ListScreen(
    tags: Map<String, List<Tag>>,
    isRefreshing: Boolean,
    onClickAddButton: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            ShoppingMemoAppBar(
                title = stringResource(R.string.tag_list_title),
                icon = Icons.Default.Label,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onClickAddButton,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        }
    ) {
        ListScreenContent(
            tags = tags,
            isRefreshing = isRefreshing,
        )
    }
}

@Composable
private fun ListScreenContent(
    tags: Map<String, List<Tag>>,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = {},
    ) {
        LazyColumn(
            modifier = modifier
                .padding(8.dp)
                .fillMaxSize()
        ) {
            tags.toList().forEach { (type, list) ->
                item {
                    Text(
                        type,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .padding(vertical = 8.dp),
                    )
                    Divider(color = MaterialTheme.colors.onBackground)
                }
                itemsIndexed(list, key = { _, item -> item.id }) { index, item ->
                    Text(
                        item.name,
                        modifier = Modifier
                            .padding(start = 32.dp)
                            .padding(vertical = 8.dp)
                    )
                    if (index < list.size - 1) {
                        Divider(
                            color = MaterialTheme.colors.onBackground,
                            startIndent = 16.dp,
                            modifier = Modifier.alpha(0.5f),
                        )
                    }
                }
            }
        }
    }
}
