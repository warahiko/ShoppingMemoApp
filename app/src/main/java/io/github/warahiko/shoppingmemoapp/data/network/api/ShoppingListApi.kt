package io.github.warahiko.shoppingmemoapp.data.network.api

import io.github.warahiko.shoppingmemoapp.data.network.model.AddShoppingItemRequest
import io.github.warahiko.shoppingmemoapp.data.network.model.ShoppingListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ShoppingListApi {

    @POST("databases/{database_id}/query")
    suspend fun getShoppingList(@Path("database_id") databaseId: String): ShoppingListResponse

    @POST("pages")
    suspend fun addShoppingItem(@Body request: AddShoppingItemRequest): Response<Unit>
}
