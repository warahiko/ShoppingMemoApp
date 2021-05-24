package io.github.warahiko.shoppingmemoapp.preview

import io.github.warahiko.shoppingmemoapp.model.ShoppingItem

fun ShoppingItem.Companion.getSample() = ShoppingItem(
    name = "にんじん",
    isDone = true,
    memo = "メモ",
)

fun ShoppingItem.Companion.getSampleList() = listOf(
    ShoppingItem(name = "にんじん", count = 1, isDone = true, memo = "memo"),
    ShoppingItem(name = "たまねぎ", count = 2, isDone = false, memo = ""),
    ShoppingItem(name = "卵", count = 1, isDone = false, memo = "memo"),
    ShoppingItem(name = "牛乳", count = 3, isDone = true, memo = ""),
)
