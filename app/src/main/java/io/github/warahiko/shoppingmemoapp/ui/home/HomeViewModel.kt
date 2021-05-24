package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.usecase.AddShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.ChangeShoppingItemIsDoneUseCase
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

    fun showAddDialog() {
        _shouldShowAddDialog.value = true
    }

    fun hideAddDialog() {
        _shouldShowAddDialog.value = false
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
}
