package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.usecase.AddShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.FetchShoppingListUseCase
import io.github.warahiko.shoppingmemoapp.usecase.UpdateShoppingItemUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchShoppingListUseCase: FetchShoppingListUseCase,
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    private val updateShoppingItemUseCase: UpdateShoppingItemUseCase,
) : ViewModel() {

    private val _shoppingListFlow = MutableStateFlow<List<ShoppingItem>>(listOf())
    val shoppingListFlow: StateFlow<List<ShoppingItem>> = _shoppingListFlow
        .map { list ->
            list.sortedBy { it.name }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    fun fetchShoppingList() = viewModelScope.launch {
        _isRefreshing.value = true
        fetchShoppingListUseCase().collect {
            _shoppingListFlow.value = it
            _isRefreshing.value = false
        }
    }

    fun addShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        addShoppingItemUseCase(shoppingItem)
            .catch {
                // TODO
            }
            .collect { shoppingItem ->
                _shoppingListFlow.value = _shoppingListFlow.value + shoppingItem
            }
    }

    fun updateShoppingItem(newShoppingItem: ShoppingItem) = viewModelScope.launch {
        updateShoppingItemUseCase(newShoppingItem).collect { isSuccessful ->
            if (isSuccessful) {
                _shoppingListFlow.value = _shoppingListFlow.value
                    .toMutableList()
                    .map {
                        if (it.id == newShoppingItem.id) newShoppingItem else it
                    }
            } else {
                // TODO
            }
        }
    }
}
