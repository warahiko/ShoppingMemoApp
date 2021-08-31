package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import javax.inject.Inject

class ChangeShoppingItemIsDoneUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(
        shoppingItem: ShoppingItem,
        newIsDone: Boolean,
    ) {
        val newShoppingItem = shoppingItem.copyWith(isDone = newIsDone)
        shoppingListRepository.updateShoppingItem(newShoppingItem)
    }
}
