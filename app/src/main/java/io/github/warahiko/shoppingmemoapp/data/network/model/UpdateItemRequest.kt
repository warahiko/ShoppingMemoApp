package io.github.warahiko.shoppingmemoapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateItemRequest(
    val properties: Map<String, Property> = emptyMap(),
    @SerialName("archived") val isArchived: Boolean = false,
)
