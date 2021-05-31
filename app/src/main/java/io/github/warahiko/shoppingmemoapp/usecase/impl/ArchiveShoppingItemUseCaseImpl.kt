package io.github.warahiko.shoppingmemoapp.usecase.impl

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.model.Status
import io.github.warahiko.shoppingmemoapp.usecase.ArchiveShoppingItemUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArchiveShoppingItemUseCaseImpl @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) : ArchiveShoppingItemUseCase {

    override suspend fun invoke(shoppingItem: ShoppingItem): Flow<ShoppingItem> {
        val archived = shoppingItem.copy(status = Status.ARCHIVED)
        return shoppingListRepository.updateShoppingItem(archived)
    }
}
