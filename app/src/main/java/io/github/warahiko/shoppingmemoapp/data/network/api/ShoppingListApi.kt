package io.github.warahiko.shoppingmemoapp.data.network.api

import io.github.warahiko.shoppingmemoapp.data.network.model.ShoppingListResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface ShoppingListApi {

    @POST("databases/{database_id}/query")
    suspend fun getShoppingList(@Path("database_id") databaseId: String): ShoppingListResponse
}
