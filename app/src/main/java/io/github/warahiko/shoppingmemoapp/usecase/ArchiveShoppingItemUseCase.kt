package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Status
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import javax.inject.Inject

class ArchiveShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(shoppingItem: ShoppingItem) {
        val archived = shoppingItem.copy(status = Status.ARCHIVED)
        shoppingListRepository.updateShoppingItem(archived)
    }
}
