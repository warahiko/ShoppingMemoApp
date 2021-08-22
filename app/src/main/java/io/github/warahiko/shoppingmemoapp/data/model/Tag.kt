package io.github.warahiko.shoppingmemoapp.data.model

import java.util.UUID

data class Tag(
    val id: UUID,
    val name: String,
    val type: String,
) {
    override fun toString() = "$type > $name"

    companion object {
        fun newInstance(
            name: String,
            type: String,
        ): Tag {
            return Tag(
                id = UUID.randomUUID(),
                name = name,
                type = type,
            )
        }
    }
}
