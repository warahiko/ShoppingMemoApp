package io.github.warahiko.shoppingmemoapp.preview

import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.model.Status
import io.github.warahiko.shoppingmemoapp.model.Tag

fun ShoppingItem.Companion.getSample() = ShoppingItem(
    name = "にんじん",
    status = Status.DONE,
    memo = "メモ",
    tag = Tag("にんじん", "野菜"),
)

fun ShoppingItem.Companion.getSampleList() = listOf(
    ShoppingItem(name = "にんじん", count = 1, status = Status.DONE, memo = "memo"),
    ShoppingItem(name = "たまねぎ", count = 2, status = Status.NEW, memo = ""),
    ShoppingItem(name = "卵", count = 1, status = Status.NEW, memo = "memo"),
    ShoppingItem(name = "牛乳", count = 3, status = Status.DONE, memo = ""),
)
