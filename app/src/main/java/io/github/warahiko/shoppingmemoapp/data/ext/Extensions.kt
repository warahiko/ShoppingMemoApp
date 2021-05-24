package io.github.warahiko.shoppingmemoapp.data.ext

import io.github.warahiko.shoppingmemoapp.data.network.model.Page
import io.github.warahiko.shoppingmemoapp.data.network.model.Property
import io.github.warahiko.shoppingmemoapp.data.network.model.RichText
import io.github.warahiko.shoppingmemoapp.data.network.model.Text
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import java.util.UUID

fun List<RichText>.concatText() = fold("") { acc, richText ->
    acc + richText.text.content
}

fun String.toRichTextList(): List<RichText> = listOf(RichText(Text(this)))

fun Page.toShoppingItem(): ShoppingItem = ShoppingItem(
    UUID.fromString(id),
    getName(),
    getCount(),
    isDone(),
    getStatus(),
    getDoneDate(),
    getMemo(),
)

fun ShoppingItem.toProperties(): Map<String, Property> = mapOf(
    "Name" to Property(title = name.toRichTextList()),
    "Count" to Property(number = count.toLong()),
    "IsDone" to Property(isChecked = isDone),
    "Memo" to Property(richText = memo.toRichTextList()),
)
