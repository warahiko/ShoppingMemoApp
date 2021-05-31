package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface DeleteShoppingItemUseCase {
    suspend operator fun invoke(shoppingItem: ShoppingItem): Flow<ShoppingItem>
}
