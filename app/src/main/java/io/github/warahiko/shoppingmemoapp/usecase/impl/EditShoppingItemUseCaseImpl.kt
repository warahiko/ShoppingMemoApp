package io.github.warahiko.shoppingmemoapp.usecase.impl

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.usecase.EditShoppingItemUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditShoppingItemUseCaseImpl @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) : EditShoppingItemUseCase {

    override suspend fun invoke(newShoppingItem: ShoppingItem): Flow<ShoppingItem> {
        return shoppingListRepository.updateShoppingItem(newShoppingItem)
    }
}
