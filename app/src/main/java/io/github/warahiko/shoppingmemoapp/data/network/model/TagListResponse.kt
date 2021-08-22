package io.github.warahiko.shoppingmemoapp.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class TagListResponse(
    val results: List<TagPage>,
)

@Serializable
data class TagPage(
    val id: String,
    val properties: Map<String, Property>,
)
