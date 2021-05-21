package io.github.warahiko.shoppingmemoapp.data.repository.impl

import io.github.warahiko.shoppingmemoapp.BuildConfig
import io.github.warahiko.shoppingmemoapp.data.network.api.ShoppingListApi
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
class ShoppingListRepositoryImpl @Inject constructor(
    private val shoppingListApi: ShoppingListApi,
) : ShoppingListRepository {

    override suspend fun getShoppingList(): Flow<List<ShoppingItem>> = flow {
        val response = shoppingListApi.getShoppingList(BuildConfig.DATABASE_ID)
        val items = response.results.map { result ->
            ShoppingItem(
                UUID.randomUUID(),
                result.getName(),
                result.getCount(),
            )
        }
        emit(items)
    }.flowOn(Dispatchers.IO)
}
