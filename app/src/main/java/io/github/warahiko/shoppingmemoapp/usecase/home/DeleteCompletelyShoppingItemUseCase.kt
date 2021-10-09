package io.github.warahiko.shoppingmemoapp.usecase.home

import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import javax.inject.Inject

class DeleteCompletelyShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(vararg shoppingItems: ShoppingItem) {
        shoppingListRepository.deleteShoppingItem(*shoppingItems)
    }
}
