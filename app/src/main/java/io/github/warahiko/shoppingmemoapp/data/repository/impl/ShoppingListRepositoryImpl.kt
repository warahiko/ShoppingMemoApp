package io.github.warahiko.shoppingmemoapp.data.repository.impl

import io.github.warahiko.shoppingmemoapp.BuildConfig
import io.github.warahiko.shoppingmemoapp.data.ext.toRichTextList
import io.github.warahiko.shoppingmemoapp.data.network.api.ShoppingListApi
import io.github.warahiko.shoppingmemoapp.data.network.model.AddShoppingItemRequest
import io.github.warahiko.shoppingmemoapp.data.network.model.Database
import io.github.warahiko.shoppingmemoapp.data.network.model.Property
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
                UUID.fromString(result.id),
                result.getName(),
                result.getCount(),
                result.isDone(),
                result.getMemo(),
            )
        }
        emit(items)
    }.flowOn(Dispatchers.IO)

    override suspend fun addShoppingItem(shoppingItem: ShoppingItem): Flow<Boolean> = flow {
        val requestBody = mapOf(
            "Name" to Property(title = shoppingItem.name.toRichTextList()),
            "Count" to Property(number = shoppingItem.count.toLong()),
            "IsDone" to Property(isChecked = shoppingItem.isDone),
            "Memo" to Property(richText = shoppingItem.memo.toRichTextList()),
        )
        val request = AddShoppingItemRequest(Database(BuildConfig.DATABASE_ID), requestBody)
        val response = shoppingListApi.addShoppingItem(request)
        emit(response.isSuccessful)
    }.flowOn(Dispatchers.IO)
}
