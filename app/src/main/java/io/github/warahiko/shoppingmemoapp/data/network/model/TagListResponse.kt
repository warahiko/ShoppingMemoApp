package io.github.warahiko.shoppingmemoapp.data.network.model

import io.github.warahiko.shoppingmemoapp.data.ext.concatText
import kotlinx.serialization.Serializable

@Serializable
data class TagListResponse(
    val results: List<TagPage>,
)

@Serializable
data class TagPage(
    val id: String,
    val properties: Map<String, Property>,
) {
    fun getName(): String = checkNotNull(properties.getValue("Name").title?.concatText())

    fun getType(): String = checkNotNull(properties.getValue("Type").select?.name)
}
