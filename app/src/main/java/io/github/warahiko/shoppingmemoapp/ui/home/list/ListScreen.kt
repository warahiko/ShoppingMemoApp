package io.github.warahiko.shoppingmemoapp.ui.home.list

import androidx.compose.foundation.layout.Column
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Status
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoScaffold
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    shoppingItems: List<ShoppingItem>,
    isRefreshing: Boolean,
    onClickAddButton: () -> Unit,
    onRefresh: () -> Unit,
    onIsDoneChange: (item: ShoppingItem, newIsDone: Boolean) -> Unit,
    onEdit: (item: ShoppingItem) -> Unit,
    onArchive: (item: ShoppingItem) -> Unit,
    onDelete: (item: ShoppingItem) -> Unit,
) {
    ShoppingMemoScaffold(
        title = stringResource(R.string.app_name),
        appBarIcon = Icons.Default.ShoppingCart,
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
            shoppingItems = shoppingItems,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            onIsDoneChange = onIsDoneChange,
            onEdit = onEdit,
            onArchive = onArchive,
            onDelete = onDelete,
        )
    }
}

@Composable
private fun ListScreenContent(
    shoppingItems: List<ShoppingItem>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onIsDoneChange: (item: ShoppingItem, newIsDone: Boolean) -> Unit,
    onEdit: (item: ShoppingItem) -> Unit,
    onArchive: (item: ShoppingItem) -> Unit,
    onDelete: (item: ShoppingItem) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = Tabs.values().size, infiniteLoop = true)
    val composableScope = rememberCoroutineScope()

    Column {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            Tabs.values().forEachIndexed { index, tabs ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        composableScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(tabs.title)
                    }
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->
            val tab = Tabs.values()[page]
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = onRefresh,
            ) {
                MainShoppingItemList(
                    shoppingItems = shoppingItems.filter {
                        it.status in tab.statusList
                    },
                    onIsDoneChange = onIsDoneChange,
                    onEdit = onEdit,
                    onArchive = onArchive,
                    onDelete = onDelete,
                )
            }
        }
    }
}

enum class Tabs(val title: String, val statusList: List<Status>) {
    Main("Main", listOf(Status.NEW, Status.DONE)),
    Archived("Archived", listOf(Status.ARCHIVED)),
    Deleted("Deleted", listOf(Status.DELETED)),
}
