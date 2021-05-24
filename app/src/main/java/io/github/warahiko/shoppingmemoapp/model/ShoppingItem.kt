package io.github.warahiko.shoppingmemoapp.model

import java.util.UUID

data class ShoppingItem(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val count: Int = 1,
    val isDone: Boolean = false,
    val memo: String = "",
) {
    companion object
}
