package io.github.warahiko.shoppingmemoapp.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AddTagRequest(
    val parent: Database,
    val properties: Map<String, Property>,
)
