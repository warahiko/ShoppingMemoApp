package io.github.warahiko.shoppingmemoapp.data.repository

import io.github.warahiko.shoppingmemoapp.BuildConfig
import io.github.warahiko.shoppingmemoapp.data.mapper.toProperties
import io.github.warahiko.shoppingmemoapp.data.mapper.toShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.network.api.ShoppingListApi
import io.github.warahiko.shoppingmemoapp.data.network.api.TagListApi
import io.github.warahiko.shoppingmemoapp.data.network.model.AddShoppingItemRequest
import io.github.warahiko.shoppingmemoapp.data.network.model.Database
import io.github.warahiko.shoppingmemoapp.data.network.model.Filter
import io.github.warahiko.shoppingmemoapp.data.network.model.GetShoppingListRequest
import io.github.warahiko.shoppingmemoapp.data.network.model.UpdateItemRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListRepository @Inject constructor(
    private val shoppingListApi: ShoppingListApi,
    private val tagListApi: TagListApi,
) {

    suspend fun getShoppingList(filter: Filter?): Flow<List<ShoppingItem>> = flow {
        val request = GetShoppingListRequest(filter = filter)
        // TODO: TagListRepository との兼ね合いを考える
        coroutineScope {
            val shoppingListAsync = async {
                shoppingListApi.getShoppingList(BuildConfig.DATABASE_ID, request)
            }
            val tagListAsync = async { tagListApi.getTagList(BuildConfig.TAG_DATABASE_ID) }
            val shoppingList = shoppingListAsync.await()
            val tagList = tagListAsync.await()
            val items = shoppingList.results.map { item ->
                val relationId = item.relations.first().id
                val tag = tagList.results.single { it.id == relationId }.let { tag ->
                    Tag(id = UUID.fromString(tag.id), name = tag.getName(), type = tag.getType())
                }
                item.toShoppingItem().copy(tag = tag)
            }
            emit(items)
        }
    }.flowOn(Dispatchers.IO)

    suspend fun addShoppingItem(shoppingItem: ShoppingItem): Flow<ShoppingItem> = flow {
        val requestBody = shoppingItem.toProperties()
        val request = AddShoppingItemRequest(Database(BuildConfig.DATABASE_ID), requestBody)
        val response = shoppingListApi.addShoppingItem(request)
        emit(response.toShoppingItem())
    }.flowOn(Dispatchers.IO)

    suspend fun updateShoppingItem(shoppingItem: ShoppingItem): Flow<ShoppingItem> = flow {
        val properties = shoppingItem.toProperties()
        val request = UpdateItemRequest(properties)
        val response = shoppingListApi.updateShoppingItem(shoppingItem.id.toString(), request)
        emit(response.toShoppingItem())
    }.flowOn(Dispatchers.IO)
}
