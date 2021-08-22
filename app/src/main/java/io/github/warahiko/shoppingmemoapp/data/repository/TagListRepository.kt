package io.github.warahiko.shoppingmemoapp.data.repository

import io.github.warahiko.shoppingmemoapp.BuildConfig
import io.github.warahiko.shoppingmemoapp.data.mapper.toTag
import io.github.warahiko.shoppingmemoapp.data.network.api.TagListApi
import io.github.warahiko.shoppingmemoapp.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagListRepository @Inject constructor(
    private val tagListApi: TagListApi,
) {

    suspend fun getTagList(): Flow<List<Tag>> = flow {
        val result = tagListApi.getTagList(BuildConfig.TAG_DATABASE_ID)
        emit(result.results.map { it.toTag() })
    }
}
