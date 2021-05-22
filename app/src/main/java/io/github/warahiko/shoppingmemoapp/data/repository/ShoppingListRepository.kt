package io.github.warahiko.shoppingmemoapp.data.repository

import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {

    suspend fun getShoppingList(): Flow<List<ShoppingItem>>

    suspend fun addShoppingItem(shoppingItem: ShoppingItem): Flow<Boolean>
}
