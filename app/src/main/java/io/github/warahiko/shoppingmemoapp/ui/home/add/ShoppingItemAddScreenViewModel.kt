package io.github.warahiko.shoppingmemoapp.ui.home.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import io.github.warahiko.shoppingmemoapp.usecase.AddShoppingItemUseCase
import javax.inject.Inject

@HiltViewModel
class ShoppingItemAddScreenViewModel @Inject constructor(
    private val addShoppingItemUseCase: AddShoppingItemUseCase,
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe {

    fun addShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        addShoppingItemUseCase(shoppingItem)
    }
}
