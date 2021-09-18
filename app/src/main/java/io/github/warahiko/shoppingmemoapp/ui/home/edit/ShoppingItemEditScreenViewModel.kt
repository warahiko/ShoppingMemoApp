package io.github.warahiko.shoppingmemoapp.ui.home.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import io.github.warahiko.shoppingmemoapp.usecase.EditShoppingItemUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ShoppingItemEditScreenViewModel @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
    private val editShoppingItemUseCase: EditShoppingItemUseCase,
    launchSafe: LaunchSafe,
) : ViewModel(), LaunchSafe by launchSafe {

    fun getShoppingItem(itemId: String): ShoppingItem? {
        return shoppingListRepository.shoppingList.value?.singleOrNull {
            it.id == UUID.fromString(itemId)
        }
    }

    fun editShoppingItem(newShoppingItem: ShoppingItem) = viewModelScope.launchSafe {
        editShoppingItemUseCase(newShoppingItem)
    }
}
