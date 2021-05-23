package io.github.warahiko.shoppingmemoapp.usecase.impl

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.usecase.AddShoppingItemUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddShoppingItemUseCaseImpl @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) : AddShoppingItemUseCase {
    override suspend fun invoke(shoppingItem: ShoppingItem): Flow<ShoppingItem> =
        shoppingListRepository.addShoppingItem(shoppingItem)
}
