package io.github.warahiko.shoppingmemoapp.data.model

import java.util.Date
import java.util.UUID

data class ShoppingItem(
    val id: UUID,
    val name: String,
    val count: Int,
    val status: Status,
    val doneDate: Date?,
    val memo: String,
    val tag: Tag?,
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

    companion object {
        fun newInstance(
            name: String,
            count: Int,
            status: Status,
            doneDate: Date?,
            memo: String,
            tag: Tag?,
        ): ShoppingItem {
            return ShoppingItem(id = UUID.randomUUID(), name, count, status, doneDate, memo, tag)
        }
    }
}

data class ShoppingItemEditable(
    val id: UUID,
    val name: String,
    val count: String,
    val status: Status,
    val doneDate: Date?,
    val memo: String,
    val tag: Tag?,
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

    companion object {
        fun newInstanceToAdd(): ShoppingItemEditable {
            return ShoppingItemEditable(
                id = UUID.randomUUID(),
                name = "",
                count = "1",
                status = Status.NEW,
                doneDate = null,
                memo = "",
                tag = null,
            )
        }
    }
}
