package io.github.warahiko.shoppingmemoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import javax.inject.Inject

@HiltViewModel
class ShoppingMemoViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe {

    fun fetchShoppingItems() = viewModelScope.launchSafe {
        shoppingListRepository.fetchShoppingList()
    }
}
