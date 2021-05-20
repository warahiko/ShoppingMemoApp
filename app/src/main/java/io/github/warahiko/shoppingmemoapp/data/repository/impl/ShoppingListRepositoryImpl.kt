package io.github.warahiko.shoppingmemoapp.data.repository.impl

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListRepositoryImpl @Inject constructor() : ShoppingListRepository {

    override suspend fun getShoppingList(): Flow<List<ShoppingItem>> = flow {
        // TODO: 仮置き
        val times = 10
        val list = (0 until times).map {
            listOf(
                ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1),
                ShoppingItem(id = UUID.randomUUID(), name = "たまねぎ", 1),
                ShoppingItem(id = UUID.randomUUID(), name = "卵", 1),
                ShoppingItem(id = UUID.randomUUID(), name = "牛乳", 3),
            )
        }.flatten()
        emit(list)
    }.flowOn(Dispatchers.IO)
}
