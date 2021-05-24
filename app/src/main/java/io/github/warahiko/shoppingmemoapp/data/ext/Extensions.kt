package io.github.warahiko.shoppingmemoapp.data.ext

import io.github.warahiko.shoppingmemoapp.data.network.model.Date
import io.github.warahiko.shoppingmemoapp.data.network.model.Page
import io.github.warahiko.shoppingmemoapp.data.network.model.Property
import io.github.warahiko.shoppingmemoapp.data.network.model.RichText
import io.github.warahiko.shoppingmemoapp.data.network.model.Select
import io.github.warahiko.shoppingmemoapp.data.network.model.Text
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

fun List<RichText>.concatText() = fold("") { acc, richText ->
    acc + richText.text.content
}

fun String.toRichTextList(): List<RichText> = listOf(RichText(Text(this)))

fun java.util.Date.toDateString(): String =
    SimpleDateFormat("yyyy-MM-dd", Locale.US).format(this)

fun Page.toShoppingItem(): ShoppingItem = ShoppingItem(
    id = UUID.fromString(id),
    name = getName(),
    count = getCount(),
    isDone = isDone(),
    status = getStatus(),
    doneDate = getDoneDate(),
    memo = getMemo(),
)

fun ShoppingItem.toProperties(): Map<String, Property> = mutableMapOf(
    "Name" to Property(title = name.toRichTextList()),
    "Count" to Property(number = count.toLong()),
    "IsDone" to Property(isChecked = isDone),
    "Status" to Property(select = Select(status.text)),
    "Memo" to Property(richText = memo.toRichTextList()),
).let {
    if (doneDate != null) {
        it += "DoneDate" to Property(date = Date(start = doneDate.toDateString()))
    }
    it
}
