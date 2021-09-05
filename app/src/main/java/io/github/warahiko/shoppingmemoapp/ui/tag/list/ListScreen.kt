package io.github.warahiko.shoppingmemoapp.ui.tag.list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoScaffold

@Composable
fun ListScreen(
    tags: List<Tag>,
    isRefreshing: Boolean,
) {
    ShoppingMemoScaffold(
        title = stringResource(R.string.tag_list_title),
        appBarIcon = Icons.Default.Tag,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
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
    tags: List<Tag>,
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
            items(tags.size, key = { tags[it].id }) { index ->
                val tag = tags[index]
                Text(tag.toString())
                if (index < tags.size - 1) {
                    Divider(color = MaterialTheme.colors.onBackground)
                }
            }
        }
    }
}
