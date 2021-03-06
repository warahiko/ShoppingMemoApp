package io.github.warahiko.shoppingmemoapp.ui.preview

import io.github.warahiko.shoppingmemoapp.data.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.data.model.Status
import io.github.warahiko.shoppingmemoapp.data.model.Tag
import java.util.Date

fun ShoppingItem.Companion.getSample() = newInstance(
    name = "にんじん",
    count = 1,
    status = Status.DONE,
    doneDate = Date(),
    memo = "メモ",
    tag = Tag.newInstance(name = "にんじん", type = "野菜"),
)

fun ShoppingItem.Companion.getSampleList() = listOf(
    newInstance(
        name = "にんじん",
        count = 1,
        status = Status.DONE,
        doneDate = Date(),
        memo = "memo",
        tag = Tag.newInstance(name = "にんじん", type = "野菜"),
    ),
    newInstance(
        name = "たまねぎ",
        count = 2,
        status = Status.NEW,
        doneDate = Date(),
        memo = "",
        tag = Tag.newInstance(name = "たまねぎ", type = "野菜"),
    ),
    newInstance(
        name = "卵",
        count = 1,
        status = Status.NEW,
        doneDate = Date(),
        memo = "memo",
        tag = Tag.newInstance(name = "卵", type = "肉"),
    ),
    newInstance(
        name = "牛乳",
        count = 3,
        status = Status.DONE,
        doneDate = Date(),
        memo = "",
        tag = Tag.newInstance(name = "牛乳", type = "飲料"),
    ),
).sortedBy {
    it.name
}

fun ShoppingItem.Companion.getSampleMap() = getSampleList()
    .groupBy { it.tag?.type.orEmpty() }
    .toSortedMap()
    .mapValues { map ->
        map.value.sortedBy { it.name }
    }
