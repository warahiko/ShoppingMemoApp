package io.github.warahiko.shoppingmemoapp.model

import java.util.UUID

data class ShoppingItem(
    val id: UUID,
    val name: String,
    val count: Int,
)
