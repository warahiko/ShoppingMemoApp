package io.github.warahiko.shoppingmemoapp.usecase.impl

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.usecase.ChangeShoppingItemIsDoneUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChangeShoppingItemIsDoneUseCaseImpl @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) : ChangeShoppingItemIsDoneUseCase {

    override suspend fun invoke(
        shoppingItem: ShoppingItem,
        newIsDone: Boolean,
    ): Flow<ShoppingItem> {
        val newShoppingItem = shoppingItem.copy(newIsDone)
        return shoppingListRepository.updateShoppingItem(newShoppingItem)
    }
}
