package io.github.warahiko.shoppingmemoapp.data.mapper

import io.github.warahiko.shoppingmemoapp.data.ext.concatText
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.network.model.TagPage
import java.util.UUID

fun TagPage.toTag(): Tag {
    return Tag(
        id = UUID.fromString(this.id),
        name = checkNotNull(properties.getValue("Name").title?.concatText()),
        type = checkNotNull(properties.getValue("Type").select?.name),
    )
}
