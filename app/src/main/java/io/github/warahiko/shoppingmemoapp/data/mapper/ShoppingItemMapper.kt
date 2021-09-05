package io.github.warahiko.shoppingmemoapp.data.mapper

import io.github.warahiko.shoppingmemoapp.data.ext.concatText
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Status
import io.github.warahiko.shoppingmemoapp.data.network.model.Date
import io.github.warahiko.shoppingmemoapp.data.network.model.Property
import io.github.warahiko.shoppingmemoapp.data.network.model.Relation
import io.github.warahiko.shoppingmemoapp.data.network.model.Select
import io.github.warahiko.shoppingmemoapp.data.network.model.ShoppingItemPage
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

fun ShoppingItemPage.toShoppingItem(): ShoppingItem {
    return ShoppingItem(
        id = UUID.fromString(id),
        name = checkNotNull(properties.getValue("Name").title?.concatText()),
        count = checkNotNull(properties.getValue("Count").number?.toInt()),
        status = checkNotNull(properties.getValue("Status").select?.let {
            Status.from(it.name)
        }),
        doneDate = properties["DoneDate"]?.date?.start?.toDate(),
        memo = checkNotNull(properties.getValue("Memo").richTexts?.concatText()),
        tag = null,
    )
}

val ShoppingItemPage.relations: List<Relation>
    get() = checkNotNull(properties.getValue("Tag").relations)

fun ShoppingItem.toProperties(): Map<String, Property> {
    return mutableMapOf(
        "Name" to Property(title = name.toRichTextList()),
        "Count" to Property(number = count.toLong()),
        "Status" to Property(select = Select(status.text)),
        "DoneDate" to Property(date = Date(start = doneDate?.toDateString() ?: "")),
        "Memo" to Property(richTexts = memo.toRichTextList()),
    ).let { map ->
        tag?.let { tag ->
            map += "Tag" to Property(relations = listOf(Relation(tag.id.toString())))
        }
        map
    }
}

private fun java.util.Date.toDateString(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(this)
}

private fun String.toDate(): java.util.Date? {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this)
}
