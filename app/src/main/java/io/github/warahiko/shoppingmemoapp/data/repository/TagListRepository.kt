package io.github.warahiko.shoppingmemoapp.data.repository

import io.github.warahiko.shoppingmemoapp.BuildConfig
import io.github.warahiko.shoppingmemoapp.data.mapper.toProperties
import io.github.warahiko.shoppingmemoapp.data.mapper.toTag
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.network.api.TagListApi
import io.github.warahiko.shoppingmemoapp.data.network.model.AddTagRequest
import io.github.warahiko.shoppingmemoapp.data.network.model.Database
import io.github.warahiko.shoppingmemoapp.error.InternalError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagListRepository @Inject constructor(
    private val tagListApi: TagListApi,
) {
    private val _tagList: MutableStateFlow<List<Tag>?> = MutableStateFlow(null)
    val tagList: StateFlow<List<Tag>?>
        get() = _tagList

    suspend fun getOrFetchTagList(): List<Tag> {
        return _tagList.value ?: fetchTagList()
    }

    fun getTagList(): List<Tag> {
        return _tagList.value ?: throw InternalError("Tag list has not been fetched yet")
    }

    suspend fun fetchTagList(): List<Tag> {
        val result = withContext(Dispatchers.IO) {
            tagListApi.getTagList(BuildConfig.TAG_DATABASE_ID)
        }
        return result.results
            .map { it.toTag() }
            .also {
                _tagList.value = it
            }
    }

    suspend fun addTag(tag: Tag) {
        val requestBody = tag.toProperties()
        val request = AddTagRequest(Database(BuildConfig.TAG_DATABASE_ID), requestBody)
        val response = withContext(Dispatchers.IO) {
            tagListApi.addTag(request)
        }
        val item = response.toTag()
        _tagList.value = _tagList.value?.plus(item) ?: listOf(item)
    }
}
