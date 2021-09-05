package io.github.warahiko.shoppingmemoapp.data.network.api

import io.github.warahiko.shoppingmemoapp.data.network.model.AddTagRequest
import io.github.warahiko.shoppingmemoapp.data.network.model.TagListResponse
import io.github.warahiko.shoppingmemoapp.data.network.model.TagPage
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface TagListApi {

    @POST("databases/{database_id}/query")
    suspend fun getTagList(
        @Path("database_id") databaseId: String,
    ): TagListResponse

    @POST("pages")
    suspend fun addTag(@Body request: AddTagRequest): TagPage
}
