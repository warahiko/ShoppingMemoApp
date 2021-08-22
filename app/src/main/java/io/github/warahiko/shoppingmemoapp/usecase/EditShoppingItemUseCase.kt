package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(newShoppingItem: ShoppingItem): Flow<ShoppingItem> {
        return shoppingListRepository.updateShoppingItem(newShoppingItem)
    }
}
