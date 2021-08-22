package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.model.Status
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(shoppingItem: ShoppingItem): Flow<ShoppingItem> {
        val deleted = shoppingItem.copy(status = Status.DELETED)
        return shoppingListRepository.updateShoppingItem(deleted)
    }
}
