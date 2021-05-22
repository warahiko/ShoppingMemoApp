package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.usecase.AddShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.FetchShoppingListUseCase
import io.github.warahiko.shoppingmemoapp.usecase.UpdateShoppingItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
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
        addShoppingItemUseCase(shoppingItem).collect()
    }

    fun updateShoppingItem(newShoppingItem: ShoppingItem) = viewModelScope.launch {
        updateShoppingItemUseCase(newShoppingItem).collect()
    }
}
