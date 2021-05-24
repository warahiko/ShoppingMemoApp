package io.github.warahiko.shoppingmemoapp.data.network.model

import io.github.warahiko.shoppingmemoapp.data.ext.concatText
import io.github.warahiko.shoppingmemoapp.model.Status
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Serializable
data class ShoppingListResponse(
    val results: List<Page>,
)

@Serializable
data class Page(
    val id: String,
    val properties: Map<String, Property>,
) {
    fun getName(): String = checkNotNull(properties.getValue("Name").title?.concatText())

    fun getCount(): Int = checkNotNull(properties.getValue("Count").number?.toInt())

    fun isDone(): Boolean = checkNotNull(properties.getValue("IsDone").isChecked)

    fun getStatus(): Status =
        checkNotNull(properties.getValue("Status").select?.let { Status.from(it.name) })

    fun getDoneDate(): Date? = properties["DoneDate"]?.date?.start?.let {
        SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(it)
    }

    fun getMemo(): String = checkNotNull(properties.getValue("Memo").richText?.concatText())
}
