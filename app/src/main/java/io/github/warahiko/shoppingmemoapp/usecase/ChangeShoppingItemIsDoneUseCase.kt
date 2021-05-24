package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ChangeShoppingItemIsDoneUseCase {

    suspend operator fun invoke(
        shoppingItem: ShoppingItem,
        newIsDone: Boolean,
    ): Flow<ShoppingItem>
}
