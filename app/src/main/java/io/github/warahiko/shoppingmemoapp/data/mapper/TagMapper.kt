package io.github.warahiko.shoppingmemoapp.data.mapper

import io.github.warahiko.shoppingmemoapp.data.ext.concatText
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import io.github.warahiko.shoppingmemoapp.data.network.model.Property
import io.github.warahiko.shoppingmemoapp.data.network.model.Select
import io.github.warahiko.shoppingmemoapp.data.network.model.TagPage
import java.util.UUID

fun TagPage.toTag(): Tag {
    return Tag(
        id = UUID.fromString(this.id),
        name = checkNotNull(properties[TagProperty.Name.key]?.title?.concatText()),
        type = checkNotNull(properties[TagProperty.Type.key]?.select?.name),
    )
}

fun Tag.toProperties(): Map<String, Property> {
    return mapOf(
        TagProperty.Name.key to Property(title = name.toRichTextList()),
        TagProperty.Type.key to Property(select = Select(type)),
    )
}

private enum class TagProperty(val key: String) {
    Name("Name"),
    Type("Type"),
}
