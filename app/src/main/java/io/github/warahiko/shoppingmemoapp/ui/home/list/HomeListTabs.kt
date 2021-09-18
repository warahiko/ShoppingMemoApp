package io.github.warahiko.shoppingmemoapp.ui.home.list

import androidx.annotation.StringRes
import io.github.warahiko.shoppingmemoapp.R
import io.github.warahiko.shoppingmemoapp.data.model.Status

enum class HomeListTabs(
    @StringRes val titleResourceId: Int,
    val statusList: List<Status>,
) {
    Main(R.string.home_list_tab_main, listOf(Status.NEW, Status.DONE)),
    Archived(R.string.home_list_tab_archived, listOf(Status.ARCHIVED)),
    Deleted(R.string.home_list_tab_deleted, listOf(Status.DELETED)),
}
