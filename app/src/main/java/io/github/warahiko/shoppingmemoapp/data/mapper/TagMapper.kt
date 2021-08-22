package io.github.warahiko.shoppingmemoapp.data.mapper

import io.github.warahiko.shoppingmemoapp.data.network.model.TagPage
import io.github.warahiko.shoppingmemoapp.model.Tag
import java.util.UUID

fun TagPage.toTag(): Tag {
    return Tag(
        id = UUID.fromString(this.id),
        name = this.getName(),
        type = this.getType(),
    )
}
