package io.github.warahiko.shoppingmemoapp.ui.home.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeListScreenViewModel @Inject constructor(
    shoppingListRepository: ShoppingListRepository,
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe {

    val mainShoppingItems: StateFlow<Map<String, List<ShoppingItem>>> =
        shoppingListRepository.shoppingList.map { list ->
            list.orEmpty()
                .filter {
                    it.status in HomeListTabs.Main.statusList
                }
                .groupBy {
                    it.tag?.type.orEmpty()
                }
                .toSortedMap()
                .mapValues { map ->
                    map.value.sortedBy { it.name }
                }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyMap())

    val archivedShoppingItems: StateFlow<List<ShoppingItem>> =
        shoppingListRepository.shoppingList.map { list ->
            list.orEmpty()
                .filter {
                    it.status in HomeListTabs.Archived.statusList
                }.sortedBy {
                    it.name
                }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    val deletedShoppingItems: StateFlow<List<ShoppingItem>> =
        shoppingListRepository.shoppingList.map { list ->
            list.orEmpty()
                .filter {
                    it.status in HomeListTabs.Deleted.statusList
                }.sortedBy {
                    it.name
                }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

}
