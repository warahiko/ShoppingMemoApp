package io.github.warahiko.shoppingmemoapp.data.model

import java.util.Date
import java.util.UUID

data class ShoppingItem(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val count: Int = 1,
    val status: Status = Status.NEW,
    val doneDate: Date? = null,
    val memo: String = "",
    val tag: Tag? = null,
) {

    val isDone: Boolean get() = status == Status.DONE
    val shouldShow: Boolean get() = this.status in listOf(Status.NEW, Status.DONE)

    fun copy(isDone: Boolean): ShoppingItem {
        check(shouldShow)
        val newStatus = if (isDone) Status.DONE else Status.NEW
        return this.copy(status = newStatus)
    }

    fun toEditable(): ShoppingItemEditable {
        return ShoppingItemEditable(
            id = id,
            name = name,
            count = count.toString(),
            status = status,
            doneDate = doneDate,
            memo = memo,
            tag = tag,
        )
    }

    companion object
}

data class ShoppingItemEditable(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val count: String = "1",
    val status: Status = Status.NEW,
    val doneDate: Date? = null,
    val memo: String = "",
    val tag: Tag? = null,
) {
    fun fix(): ShoppingItem {
        return ShoppingItem(
            id = id,
            name = name,
            count = count.toInt(),
            status = status,
            doneDate = doneDate,
            memo = memo,
            tag = tag,
        )
    }
}
