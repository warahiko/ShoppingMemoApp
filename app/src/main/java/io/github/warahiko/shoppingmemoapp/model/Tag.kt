package io.github.warahiko.shoppingmemoapp.model

import java.util.UUID

data class Tag(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val type: String,
) {
    override fun toString() = "$type > $name"
}
