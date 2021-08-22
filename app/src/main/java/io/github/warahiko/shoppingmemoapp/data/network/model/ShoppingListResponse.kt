package io.github.warahiko.shoppingmemoapp.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListResponse(
    val results: List<ShoppingListPage>,
)

@Serializable
data class ShoppingListPage(
    val id: String,
    val properties: Map<String, Property>,
)
