package io.github.warahiko.shoppingmemoapp.data.ext

import io.github.warahiko.shoppingmemoapp.data.network.model.Page
import io.github.warahiko.shoppingmemoapp.data.network.model.RichText
import io.github.warahiko.shoppingmemoapp.data.network.model.Text
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import java.util.UUID

fun List<RichText>.concatText() = fold("") { acc, richText ->
    acc + richText.text.content
}

fun String.toRichTextList(): List<RichText> = listOf(RichText(Text(this)))

fun Page.toShoppingItem(): ShoppingItem = ShoppingItem(
    UUID.fromString(this.id),
    this.getName(),
    this.getCount(),
    this.isDone(),
    this.getMemo(),
)
