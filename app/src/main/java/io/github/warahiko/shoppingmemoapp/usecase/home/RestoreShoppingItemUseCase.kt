package io.github.warahiko.shoppingmemoapp.usecase.home

import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Status
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import javax.inject.Inject

class RestoreShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(shoppingItem: ShoppingItem) {
        val newStatus = if (shoppingItem.doneDate == null) Status.NEW else Status.DONE
        val restored = shoppingItem.copy(status = newStatus)
        shoppingListRepository.updateShoppingItem(restored)
    }
}
