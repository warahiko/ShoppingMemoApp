package io.github.warahiko.shoppingmemoapp.ui.home.list

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.ui.ShoppingMemoAppBar
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    shoppingItems: Map<String, List<ShoppingItem>>,
    isRefreshing: Boolean,
    onClickAddButton: () -> Unit,
    onRefresh: () -> Unit,
    onClickItemRow: (item: ShoppingItem) -> Unit,
    onEdit: (item: ShoppingItem) -> Unit,
    onArchive: (item: ShoppingItem) -> Unit,
    onDelete: (item: ShoppingItem) -> Unit,
    onArchiveAll: () -> Unit,
    viewModel: HomeListScreenViewModel = hiltViewModel(),
) {
    val mainShoppingItems by viewModel.mainShoppingItems.collectAsState()

    Scaffold(
        topBar = {
            ShoppingMemoAppBar(
                title = stringResource(R.string.home_list_title),
                icon = Icons.Default.ShoppingCart,
            )
        },
    ) {
        ListScreenContent(
            mainShoppingItems = mainShoppingItems,
            shoppingItems = shoppingItems,
            isRefreshing = isRefreshing,
            onClickAddButton = onClickAddButton,
            onRefresh = onRefresh,
            onClickItemRow = onClickItemRow,
            onEdit = onEdit,
            onArchive = onArchive,
            onDelete = onDelete,
            onArchiveAll = onArchiveAll,
        )
    }
}

@Composable
private fun ListScreenContent(
    mainShoppingItems: Map<String, List<ShoppingItem>>,
    shoppingItems: Map<String, List<ShoppingItem>>,
    isRefreshing: Boolean,
    onClickAddButton: () -> Unit,
    onRefresh: () -> Unit,
    onClickItemRow: (item: ShoppingItem) -> Unit,
    onEdit: (item: ShoppingItem) -> Unit,
    onArchive: (item: ShoppingItem) -> Unit,
    onDelete: (item: ShoppingItem) -> Unit,
    onArchiveAll: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = HomeListTabs.values().size, infiniteLoop = true)
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
            HomeListTabs.values().forEachIndexed { index, tabs ->
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
            val tab = HomeListTabs.values()[page]
            // TODO: viewModel に移行
            val filteredShoppingItems = shoppingItems.mapValues { map ->
                map.value.filter {
                    it.status in tab.statusList
                }
            }.filterValues { it.isNotEmpty() }
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = onRefresh,
            ) {
                when (tab) {
                    HomeListTabs.Main -> {
                        MainShoppingItemList(
                            shoppingItems = mainShoppingItems,
                            onClickAddButton = onClickAddButton,
                            onClickItemRow = onClickItemRow,
                            onEdit = onEdit,
                            onArchive = onArchive,
                            onDelete = onDelete,
                            onArchiveAll = onArchiveAll,
                        )
                    }
                    HomeListTabs.Archived -> {
                        ArchivedShoppingItemList(
                            // FIXME
                            shoppingItems = filteredShoppingItems.values.flatten(),
                            onDelete = onDelete,
                        )
                    }
                    HomeListTabs.Deleted -> {
                        DeletedShoppingItemList(
                            // FIXME
                            shoppingItems = filteredShoppingItems.values.flatten(),
                        )
                    }
                }
            }
        }
    }
}
