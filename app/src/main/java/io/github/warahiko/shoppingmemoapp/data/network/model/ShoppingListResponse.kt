package io.github.warahiko.shoppingmemoapp.data.network.model

import io.github.warahiko.shoppingmemoapp.data.ext.concatText
import io.github.warahiko.shoppingmemoapp.data.model.Status
import kotlinx.serialization.Serializable

@Serializable
data class ShoppingListResponse(
    val results: List<ShoppingListPage>,
)

@Serializable
data class ShoppingListPage(
    val id: String,
    val properties: Map<String, Property>,
) {
    val name: String get() = checkNotNull(properties.getValue("Name").title?.concatText())
    val count: Int get() = checkNotNull(properties.getValue("Count").number?.toInt())
    val status: Status
        get() = checkNotNull(properties.getValue("Status").select?.let {
            Status.from(it.name)
        })
    val doneDateString: String? get() = properties["DoneDate"]?.date?.start
    val memo: String get() = checkNotNull(properties.getValue("Memo").richTexts?.concatText())
    val relations: List<Relation> get() = checkNotNull(properties.getValue("Tag").relations)
}
