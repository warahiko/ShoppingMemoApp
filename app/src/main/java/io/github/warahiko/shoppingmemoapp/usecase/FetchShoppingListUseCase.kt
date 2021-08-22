package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface FetchShoppingListUseCase {
    // TODO: 各usecase のinterface を消す
    suspend operator fun invoke(): Flow<List<ShoppingItem>>
}
