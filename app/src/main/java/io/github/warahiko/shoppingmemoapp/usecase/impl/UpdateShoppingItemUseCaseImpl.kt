package io.github.warahiko.shoppingmemoapp.usecase.impl

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.usecase.UpdateShoppingItemUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateShoppingItemUseCaseImpl @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) : UpdateShoppingItemUseCase {
    override suspend fun invoke(shoppingItem: ShoppingItem): Flow<ShoppingItem> =
        shoppingListRepository.updateShoppingItem(shoppingItem)
}
