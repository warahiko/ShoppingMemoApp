package io.github.warahiko.shoppingmemoapp.data.mapper

import io.github.warahiko.shoppingmemoapp.data.network.model.TagPage
import io.github.warahiko.shoppingmemoapp.model.Tag

fun TagPage.toTag(): Tag {
    return Tag(
        name = this.getName(),
        type = this.getType(),
    )
}
