package io.github.warahiko.shoppingmemoapp.data.network.api

import io.github.warahiko.shoppingmemoapp.data.network.model.AddShoppingItemRequest
import io.github.warahiko.shoppingmemoapp.data.network.model.GetShoppingListRequest
import io.github.warahiko.shoppingmemoapp.data.network.model.ShoppingItemListResponse
import io.github.warahiko.shoppingmemoapp.data.network.model.ShoppingItemPage
import io.github.warahiko.shoppingmemoapp.data.network.model.UpdateItemRequest
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ShoppingListApi {

    @POST("databases/{database_id}/query")
    suspend fun getShoppingList(
        @Path("database_id") databaseId: String,
        @Body request: GetShoppingListRequest,
    ): ShoppingItemListResponse

    @POST("pages")
    suspend fun addShoppingItem(@Body request: AddShoppingItemRequest): ShoppingItemPage

    @PATCH("pages/{page_id}")
    suspend fun updateShoppingItem(
        @Path("page_id") pageId: String,
        @Body request: UpdateItemRequest,
    ): ShoppingItemPage
}
