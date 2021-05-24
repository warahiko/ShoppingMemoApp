package io.github.warahiko.shoppingmemoapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Property(
    val title: List<RichText>? = null,
    val number: Long? = null,
    val select: Select? = null,
    val date: Date? = null,
    @SerialName("rich_text") val richText: List<RichText>? = null,
    @SerialName("checkbox") val isChecked: Boolean? = null,
)

@Serializable
data class RichText(
    val text: Text,
)

@Serializable
data class Text(
    val content: String,
)

@Serializable
data class Select(
    val name: String,
)

@Serializable
data class Date(
    val start: String,
)
