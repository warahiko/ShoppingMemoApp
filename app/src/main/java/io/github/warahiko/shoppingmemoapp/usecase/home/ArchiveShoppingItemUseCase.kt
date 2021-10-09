package io.github.warahiko.shoppingmemoapp.usecase.home

import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Status
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import javax.inject.Inject

class ArchiveShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(vararg shoppingItems: ShoppingItem) {
        val archived = shoppingItems.map { it.copy(status = Status.ARCHIVED) }
        shoppingListRepository.updateShoppingItem(*archived.toTypedArray())
    }
}
