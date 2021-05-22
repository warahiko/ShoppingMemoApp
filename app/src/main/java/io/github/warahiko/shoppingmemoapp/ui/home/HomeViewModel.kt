package io.github.warahiko.shoppingmemoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.usecase.AddShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.FetchShoppingListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchShoppingListUseCase: FetchShoppingListUseCase,
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
) : ViewModel() {

    private val _shoppingListFlow = MutableStateFlow<List<ShoppingItem>>(listOf())
    val shoppingListFlow: StateFlow<List<ShoppingItem>> = _shoppingListFlow

    fun fetchShoppingList() = viewModelScope.launch {
        fetchShoppingListUseCase().collect {
            _shoppingListFlow.value = it
        }
    }

    fun addShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        addShoppingItemUseCase(shoppingItem).collect()
    }
}
