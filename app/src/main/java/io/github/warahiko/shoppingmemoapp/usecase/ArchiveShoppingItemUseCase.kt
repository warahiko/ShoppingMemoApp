package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.model.Status
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArchiveShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(shoppingItem: ShoppingItem): Flow<ShoppingItem> {
        val archived = shoppingItem.copy(status = Status.ARCHIVED)
        return shoppingListRepository.updateShoppingItem(archived)
    }
}
