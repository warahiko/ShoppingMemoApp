package io.github.warahiko.shoppingmemoapp.data.repository

import io.github.warahiko.shoppingmemoapp.BuildConfig
import io.github.warahiko.shoppingmemoapp.data.mapper.relations
import io.github.warahiko.shoppingmemoapp.data.mapper.toProperties
import io.github.warahiko.shoppingmemoapp.data.mapper.toShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.network.api.ShoppingListApi
import io.github.warahiko.shoppingmemoapp.data.network.model.AddShoppingItemRequest
import io.github.warahiko.shoppingmemoapp.data.network.model.Database
import io.github.warahiko.shoppingmemoapp.data.network.model.GetShoppingListRequest
import io.github.warahiko.shoppingmemoapp.data.network.model.UpdateItemRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListRepository @Inject constructor(
    private val shoppingListApi: ShoppingListApi,
    private val tagListRepository: TagListRepository,
) {
    private val _shoppingList: MutableStateFlow<List<ShoppingItem>?> = MutableStateFlow(null)
    val shoppingList: StateFlow<List<ShoppingItem>?> = _shoppingList

    suspend fun getOrFetchShoppingList(): List<ShoppingItem> {
        return _shoppingList.value ?: fetchShoppingList()
    }

    suspend fun fetchShoppingList(): List<ShoppingItem> {
        val request = GetShoppingListRequest()
        val (shoppingList, tagList) = withContext(Dispatchers.IO) {
            val shoppingListAsync = async {
                shoppingListApi.getShoppingList(BuildConfig.DATABASE_ID, request)
            }
            val tagListAsync = async { tagListRepository.getOrFetchTagList() }
            shoppingListAsync.await() to tagListAsync.await()
        }
        return shoppingList.results.map { item ->
            val relationId = item.relations.first().id
            val tag = tagList.single { it.id.toString() == relationId }
            item.toShoppingItem().copy(tag = tag)
        }.also {
            _shoppingList.value = it
        }
    }

    suspend fun addShoppingItem(shoppingItem: ShoppingItem) {
        val requestBody = shoppingItem.toProperties()
        val request = AddShoppingItemRequest(Database(BuildConfig.DATABASE_ID), requestBody)
        val response = withContext(Dispatchers.IO) {
            shoppingListApi.addShoppingItem(request)
        }
        val tagList = tagListRepository.getTagList()
        val relationId = response.relations.first().id
        val tag = tagList.single { it.id.toString() == relationId }
        val item = response.toShoppingItem().copy(tag = tag)
        _shoppingList.value = _shoppingList.value?.plus(item) ?: listOf(item)
    }

    suspend fun updateShoppingItem(vararg shoppingItems: ShoppingItem) {
        val requests = shoppingItems.map { shoppingItem ->
            val properties = shoppingItem.toProperties()
            UpdateItemRequest(properties)
        }
        val responses = withContext(Dispatchers.IO) {
            shoppingItems.zip(requests).map { (shoppingItem, request) ->
                async {
                    shoppingListApi.updateShoppingItem(shoppingItem.id.toString(), request)
                }
            }.awaitAll()
        }
        val tagList = tagListRepository.getTagList()
        val items = responses.map { shoppingItemPage ->
            val relationId = shoppingItemPage.relations.first().id
            val tag = tagList.single { it.id.toString() == relationId }
            shoppingItemPage.toShoppingItem().copy(tag = tag)
        }
        _shoppingList.value = _shoppingList.value?.map { shoppingItem ->
            items.singleOrNull { it.id == shoppingItem.id } ?: shoppingItem
        }
    }
}
