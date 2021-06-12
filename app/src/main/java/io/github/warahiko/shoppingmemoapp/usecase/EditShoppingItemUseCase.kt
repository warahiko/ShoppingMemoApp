package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface EditShoppingItemUseCase {

    suspend operator fun invoke(
        newShoppingItem: ShoppingItem,
    ): Flow<ShoppingItem>
}
