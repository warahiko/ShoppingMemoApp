package io.github.warahiko.shoppingmemoapp.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ShoppingItemListResponse(
    val results: List<ShoppingItemPage>,
)

@Serializable
data class ShoppingItemPage(
    val id: String,
    val properties: Map<String, Property>,
)
