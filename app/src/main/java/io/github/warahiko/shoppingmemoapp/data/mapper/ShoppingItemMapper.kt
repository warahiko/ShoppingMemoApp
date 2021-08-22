package io.github.warahiko.shoppingmemoapp.data.mapper

import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.network.model.Date
import io.github.warahiko.shoppingmemoapp.data.network.model.Page
import io.github.warahiko.shoppingmemoapp.data.network.model.Property
import io.github.warahiko.shoppingmemoapp.data.network.model.Relation
import io.github.warahiko.shoppingmemoapp.data.network.model.RichText
import io.github.warahiko.shoppingmemoapp.data.network.model.Select
import io.github.warahiko.shoppingmemoapp.data.network.model.Text
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

fun Page.toShoppingItem(): ShoppingItem {
    return ShoppingItem(
        id = UUID.fromString(id),
        name = name,
        count = count,
        status = status,
        doneDate = doneDate,
        memo = memo,
    )
}

fun ShoppingItem.toProperties(): Map<String, Property> {
    return mutableMapOf(
        "Name" to Property(title = name.toRichTextList()),
        "Count" to Property(number = count.toLong()),
        "Status" to Property(select = Select(status.text)),
        "Memo" to Property(richText = memo.toRichTextList()),
    ).let { map ->
        doneDate?.let { doneDate ->
            map += "DoneDate" to Property(date = Date(start = doneDate.toDateString()))
        }
        tag?.let { tag ->
            map += "Tag" to Property(relation = listOf(Relation(tag.id.toString())))
        }
        map
    }
}

private fun String.toRichTextList(): List<RichText> {
    return listOf(RichText(Text(this)))
}

private fun java.util.Date.toDateString(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(this)
}
