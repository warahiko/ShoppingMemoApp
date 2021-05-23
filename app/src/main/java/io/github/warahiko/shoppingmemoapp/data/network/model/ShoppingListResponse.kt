package io.github.warahiko.shoppingmemoapp.data.network.model

import io.github.warahiko.shoppingmemoapp.data.ext.concatText
import kotlinx.serialization.Serializable

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

    fun getMemo(): String = checkNotNull(properties.getValue("Memo").richText?.concatText())
}
