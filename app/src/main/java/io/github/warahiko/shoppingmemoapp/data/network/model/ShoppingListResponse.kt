package io.github.warahiko.shoppingmemoapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListResponse(
    val results: List<Result>,
)

@Serializable
data class Result(
    val properties: Map<String, Property>,
) {
    fun getName(): String = checkNotNull(properties.getValue("Name").title?.concatText())

    fun getCount(): Int = checkNotNull(properties.getValue("Count").number?.toInt())
}

@Serializable
data class Property(
    val title: List<RichText>? = null,
    val number: Long? = null,
    @SerialName("rich_text") val richText: List<RichText>? = null,
    @SerialName("checkbox") val isChecked: Boolean? = null,
)

@Serializable
data class RichText(
    val text: Text,
)

private fun List<RichText>.concatText() = fold("") { acc, richText ->
    acc + richText.text.content
}

@Serializable
data class Text(
    val content: String,
)
