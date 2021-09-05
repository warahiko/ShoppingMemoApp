package io.github.warahiko.shoppingmemoapp.ui.home.list

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Status
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoAppBar
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    shoppingItems: List<ShoppingItem>,
    isRefreshing: Boolean,
    onClickAddButton: () -> Unit,
    onRefresh: () -> Unit,
    onClickItemRow: (item: ShoppingItem) -> Unit,
    onEdit: (item: ShoppingItem) -> Unit,
    onArchive: (item: ShoppingItem) -> Unit,
    onDelete: (item: ShoppingItem) -> Unit,
) {
    Scaffold(
        topBar = {
            ShoppingMemoAppBar(
                title = stringResource(R.string.home_list_title),
                icon = Icons.Default.ShoppingCart,
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
            shoppingItems = shoppingItems,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            onClickItemRow = onClickItemRow,
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
    onClickItemRow: (item: ShoppingItem) -> Unit,
    onEdit: (item: ShoppingItem) -> Unit,
    onArchive: (item: ShoppingItem) -> Unit,
    onDelete: (item: ShoppingItem) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = Tabs.values().size, infiniteLoop = true)
    val composableScope = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            Tabs.values().forEachIndexed { index, tabs ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        composableScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(stringResource(tabs.titleResourceId))
                    }
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->
            val tab = Tabs.values()[page]
            val filteredShoppingItems = shoppingItems.filter {
                it.status in tab.statusList
            }
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = onRefresh,
            ) {
                when (tab) {
                    Tabs.Main -> {
                        MainShoppingItemList(
                            shoppingItems = filteredShoppingItems,
                            onClickItemRow = onClickItemRow,
                            onEdit = onEdit,
                            onArchive = onArchive,
                            onDelete = onDelete,
                        )
                    }
                    Tabs.Archived -> {
                        ArchivedShoppingItemList(
                            shoppingItems = filteredShoppingItems,
                            onDelete = onDelete,
                        )
                    }
                    Tabs.Deleted -> {
                        DeletedShoppingItemList(
                            shoppingItems = filteredShoppingItems,
                        )
                    }
                }
            }
        }
    }
}

enum class Tabs(
    @StringRes val titleResourceId: Int,
    val statusList: List<Status>,
) {
    Main(R.string.home_list_tab_main, listOf(Status.NEW, Status.DONE)),
    Archived(R.string.home_list_tab_archived, listOf(Status.ARCHIVED)),
    Deleted(R.string.home_list_tab_deleted, listOf(Status.DELETED)),
}
