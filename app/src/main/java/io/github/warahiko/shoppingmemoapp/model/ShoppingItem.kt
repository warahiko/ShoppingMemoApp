package io.github.warahiko.shoppingmemoapp.model

import java.util.Date
import java.util.UUID

data class ShoppingItem(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val count: Int = 1,
    val isDone: Boolean = false,
    val status: Status = Status.NEW,
    val doneDate: Date? = null,
    val memo: String = "",
) {
    companion object
}
