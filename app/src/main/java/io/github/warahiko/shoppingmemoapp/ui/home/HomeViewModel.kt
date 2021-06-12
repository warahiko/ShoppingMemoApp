package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.usecase.AddShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.ArchiveShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.ChangeShoppingItemIsDoneUseCase
import io.github.warahiko.shoppingmemoapp.usecase.DeleteShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.EditShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.FetchShoppingListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchShoppingListUseCase: FetchShoppingListUseCase,
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    private val changeShoppingItemIsDoneUseCase: ChangeShoppingItemIsDoneUseCase,
    private val editShoppingItemUseCase: EditShoppingItemUseCase,
    private val archiveShoppingItemUseCase: ArchiveShoppingItemUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe {

    private val _shoppingListFlow = MutableStateFlow<List<ShoppingItem>>(listOf())
    val shoppingListFlow: StateFlow<List<ShoppingItem>> = _shoppingListFlow
        .map { list ->
            list.sortedBy { it.name }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private val _shouldShowAddDialog = MutableStateFlow(false)
    val shouldShowAddDialog: StateFlow<Boolean> = _shouldShowAddDialog

    private val _itemToEdit = MutableStateFlow<ShoppingItem?>(null)
    val itemToEdit: StateFlow<ShoppingItem?>
        get() = _itemToEdit

    private val _shoppingItemToOperate = MutableStateFlow<ShoppingItem?>(null)
    val shoppingItemToOperate: StateFlow<ShoppingItem?> = _shoppingItemToOperate

    fun showAddDialog() {
        _shouldShowAddDialog.value = true
    }

    fun hideAddDialog() {
        _shouldShowAddDialog.value = false
    }

    fun showOperationDialog(shoppingItem: ShoppingItem) {
        _shoppingItemToOperate.value = shoppingItem
    }

    fun hideOperationDialog() {
        _shoppingItemToOperate.value = null
    }

    fun fetchShoppingList() = viewModelScope.launchSafe {
        _isRefreshing.value = true
        fetchShoppingListUseCase().collect {
            _shoppingListFlow.value = it
            _isRefreshing.value = false
        }
    }

    fun addShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        check(shouldShowAddDialog.value)
        addShoppingItemUseCase(shoppingItem)
            .catch { error ->
                _shouldShowAddDialog.value = false
                throw error
            }
            .collect { resultItem ->
                _shoppingListFlow.value = _shoppingListFlow.value + resultItem
                _shouldShowAddDialog.value = false
            }
    }

    fun changeShoppingItemIsDone(shoppingItem: ShoppingItem, newIsDone: Boolean) =
        viewModelScope.launchSafe {
            changeShoppingItemIsDoneUseCase(shoppingItem, newIsDone).collect { resultItem ->
                _shoppingListFlow.value = _shoppingListFlow.value
                    .toMutableList()
                    .map {
                        if (it.id == resultItem.id) resultItem else it
                    }
            }
        }

    fun showEditingDialog(itemToEdit: ShoppingItem) {
        _itemToEdit.value = itemToEdit
        _shoppingItemToOperate.value = null
    }

    fun hideEditingDialog() {
        _itemToEdit.value = null
    }

    fun editShoppingItem(newShoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        editShoppingItemUseCase(newShoppingItem).collect { resultItem ->
            _shoppingListFlow.value = _shoppingListFlow.value
                .toMutableList()
                .map {
                    if (it.id == resultItem.id) resultItem else it
                }
            _itemToEdit.value = null
        }
    }

    fun archiveShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        archiveShoppingItemUseCase(shoppingItem).collect { resultItem ->
            _shoppingListFlow.value = _shoppingListFlow.value
                .filter { it.id != resultItem.id }
            _shoppingItemToOperate.value = null
        }
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        deleteShoppingItemUseCase(shoppingItem).collect { resultItem ->
            _shoppingListFlow.value = _shoppingListFlow.value
                .filter { it.id != resultItem.id }
            _shoppingItemToOperate.value = null
        }
    }
}
