package io.github.warahiko.shoppingmemoapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddShoppingItemRequest(
    val parent: Database,
    val properties: Map<String, Property>,
)

@Serializable
data class Database(@SerialName("database_id") val id: String)
