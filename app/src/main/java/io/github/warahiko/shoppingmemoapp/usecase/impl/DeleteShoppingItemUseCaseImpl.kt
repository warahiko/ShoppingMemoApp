package io.github.warahiko.shoppingmemoapp.usecase.impl

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.model.Status
import io.github.warahiko.shoppingmemoapp.usecase.DeleteShoppingItemUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteShoppingItemUseCaseImpl @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) : DeleteShoppingItemUseCase {

    override suspend fun invoke(shoppingItem: ShoppingItem): Flow<ShoppingItem> {
        val deleted = shoppingItem.copy(status = Status.DELETED)
        return shoppingListRepository.updateShoppingItem(deleted)
    }
}
