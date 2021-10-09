package io.github.warahiko.shoppingmemoapp.usecase.home

import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Status
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import java.util.Date
import javax.inject.Inject

class ChangeShoppingItemIsDoneUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(
        shoppingItem: ShoppingItem,
        newIsDone: Boolean,
    ) {
        val newStatus = if (newIsDone) Status.DONE else Status.NEW
        val newDoneDate = if (newIsDone) Date() else null
        val newShoppingItem = shoppingItem.copy(status = newStatus, doneDate = newDoneDate)
        shoppingListRepository.updateShoppingItem(newShoppingItem)
    }
}
