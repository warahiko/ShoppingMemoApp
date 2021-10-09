package io.github.warahiko.shoppingmemoapp.ui.home.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import io.github.warahiko.shoppingmemoapp.ui.common.ext.withLoading
import io.github.warahiko.shoppingmemoapp.usecase.home.ArchiveShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.home.ChangeShoppingItemIsDoneUseCase
import io.github.warahiko.shoppingmemoapp.usecase.home.DeleteCompletelyShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.home.DeleteShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.home.RestoreShoppingItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeListScreenViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
    private val changeShoppingItemIsDoneUseCase: ChangeShoppingItemIsDoneUseCase,
    private val archiveShoppingItemUseCase: ArchiveShoppingItemUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    private val restoreShoppingItemUseCase: RestoreShoppingItemUseCase,
    private val deleteCompletelyShoppingItemUseCase: DeleteCompletelyShoppingItemUseCase,
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

    val archivedShoppingItems: StateFlow<Map<String, List<ShoppingItem>>> =
        shoppingListRepository.shoppingList.map { list ->
            list.orEmpty()
                .filter {
                    it.status in HomeListTabs.Archived.statusList
                }
                .groupBy { it.doneDate?.toDateString().orEmpty() }
                .toSortedMap(compareByDescending { it })
                .mapValues { map ->
                    map.value.sortedBy { it.name }
                }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyMap())

    private fun Date.toDateString(): String {
        return SimpleDateFormat("yyyy/MM/dd", Locale.US).format(this)
    }

    val deletedShoppingItems: StateFlow<List<ShoppingItem>> =
        shoppingListRepository.shoppingList.map { list ->
            list.orEmpty()
                .filter {
                    it.status in HomeListTabs.Deleted.statusList
                }.sortedBy {
                    it.name
                }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(1000), emptyList())

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing

    private val _deleteEvent = MutableStateFlow(DeleteEvent.None)
    val deleteEvent: StateFlow<DeleteEvent> get() = _deleteEvent

    fun fetchShoppingList() = viewModelScope.launchSafe {
        shoppingListRepository.fetchShoppingList()
    }.withLoading(_isRefreshing)

    fun changeShoppingItemIsDone(shoppingItem: ShoppingItem) =
        viewModelScope.launchSafe {
            changeShoppingItemIsDoneUseCase(shoppingItem, !shoppingItem.isDone)
        }

    fun archiveShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        archiveShoppingItemUseCase(shoppingItem)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        deleteShoppingItemUseCase(shoppingItem)
    }

    fun restoreShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        restoreShoppingItemUseCase(shoppingItem)
    }

    fun archiveAllDone() = viewModelScope.launchSafe {
        val doneList = mainShoppingItems.value.values.flatten().filter { it.isDone }
        archiveShoppingItemUseCase(*doneList.toTypedArray())
    }

    fun showDeleteCompletelyConfirmationDialog() {
        _deleteEvent.value = DeleteEvent.ShowConfirmationDialog
    }

    fun dismissDeleteCompletelyConfirmationDialog() {
        _deleteEvent.value = DeleteEvent.None
    }

    fun deleteCompletelyShoppingItems() = viewModelScope.launchSafe {
        _deleteEvent.value = DeleteEvent.ShowProgressDialog
        deleteCompletelyShoppingItemUseCase(*deletedShoppingItems.value.toTypedArray())
        _deleteEvent.value = DeleteEvent.None
    }

    enum class DeleteEvent {
        None,
        ShowConfirmationDialog,
        ShowProgressDialog,
    }
}
