package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeShoppingItemIsDoneUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(
        shoppingItem: ShoppingItem,
        newIsDone: Boolean,
    ): Flow<ShoppingItem> {
        val newShoppingItem = shoppingItem.copy(newIsDone)
        return shoppingListRepository.updateShoppingItem(newShoppingItem)
    }
}
