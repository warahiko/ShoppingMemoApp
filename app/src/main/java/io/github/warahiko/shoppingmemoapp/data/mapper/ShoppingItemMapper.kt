package io.github.warahiko.shoppingmemoapp.data.mapper

import io.github.warahiko.shoppingmemoapp.data.ext.concatText
import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Status
import io.github.warahiko.shoppingmemoapp.data.network.model.Date
import io.github.warahiko.shoppingmemoapp.data.network.model.Relation
import io.github.warahiko.shoppingmemoapp.data.network.model.Select
import io.github.warahiko.shoppingmemoapp.data.network.model.ShoppingItemPage
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID
import io.github.warahiko.shoppingmemoapp.data.network.model.Property as PropertyModel

fun ShoppingItemPage.toShoppingItem(): ShoppingItem {
    return ShoppingItem(
        id = UUID.fromString(id),
        name = checkNotNull(properties[Property.Name.key]?.title?.concatText()),
        count = checkNotNull(properties[Property.Count.key]?.number?.toInt()),
        status = checkNotNull(properties[Property.Status.key]?.select?.let {
            Status.from(it.name)
        }),
        doneDate = properties[Property.DoneDate.key]?.date?.start?.toDate(),
        memo = checkNotNull(properties[Property.Memo.key]?.richTexts?.concatText()),
        tag = null,
    )
}

val ShoppingItemPage.relations: List<Relation>
    get() = checkNotNull(properties[Property.Tag.key]?.relations)

fun ShoppingItem.toProperties(): Map<String, PropertyModel> {
    return mutableMapOf(
        Property.Name.key to PropertyModel(title = name.toRichTextList()),
        Property.Count.key to PropertyModel(number = count.toLong()),
        Property.Status.key to PropertyModel(select = Select(status.text)),
        Property.DoneDate.key to PropertyModel(date = Date(start = doneDate?.toDateString() ?: "")),
        Property.Memo.key to PropertyModel(richTexts = memo.toRichTextList()),
    ).let { map ->
        tag?.let { tag ->
            map += Property.Tag.key to PropertyModel(relations = listOf(Relation(tag.id.toString())))
        }
        map
    }
}

private enum class Property(val key: String) {
    Name("Name"),
    Count("Count"),
    Status("Status"),
    DoneDate("DoneDate"),
    Memo("Memo"),
    Tag("Tag"),
}

private fun java.util.Date.toDateString(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(this)
}

private fun String.toDate(): java.util.Date? {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this)
}
