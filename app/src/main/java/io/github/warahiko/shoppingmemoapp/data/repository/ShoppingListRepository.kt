package io.github.warahiko.shoppingmemoapp.data.repository

import io.github.warahiko.shoppingmemoapp.model.ShoppingItem

interface ShoppingListRepository {

    fun getShoppingList(): List<ShoppingItem>
}
