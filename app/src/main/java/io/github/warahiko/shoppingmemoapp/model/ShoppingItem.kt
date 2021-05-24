package io.github.warahiko.shoppingmemoapp.model

import java.util.Date
import java.util.UUID

data class ShoppingItem(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val count: Int = 1,
    val status: Status = Status.NEW,
    val doneDate: Date? = null,
    val memo: String = "",
) {

    fun isDone() = status == Status.DONE

    fun shouldShow() = this.status in listOf(Status.NEW, Status.DONE)

    fun copy(isDone: Boolean): ShoppingItem {
        check(shouldShow())
        val newStatus = if (isDone) Status.DONE else Status.NEW
        return this.copy(status = newStatus)
    }

    companion object
}
