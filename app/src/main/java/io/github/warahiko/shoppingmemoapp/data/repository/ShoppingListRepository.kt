package io.github.warahiko.shoppingmemoapp.data.repository

import io.github.warahiko.shoppingmemoapp.data.network.model.Filter
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {

    suspend fun getShoppingList(filter: Filter? = null): Flow<List<ShoppingItem>>

    suspend fun addShoppingItem(shoppingItem: ShoppingItem): Flow<ShoppingItem>

    suspend fun updateShoppingItem(shoppingItem: ShoppingItem): Flow<ShoppingItem>
}
