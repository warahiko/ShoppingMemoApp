package io.github.warahiko.shoppingmemoapp.data.repository.impl

import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingListRepositoryImpl @Inject constructor() : ShoppingListRepository {

    override fun getShoppingList(): List<ShoppingItem> {
        // TODO: 仮置き
        val times = 10
        return (0 until times).map {
            listOf(
                ShoppingItem(id = UUID.randomUUID(), name = "にんじん", 1),
                ShoppingItem(id = UUID.randomUUID(), name = "たまねぎ", 1),
                ShoppingItem(id = UUID.randomUUID(), name = "卵", 1),
                ShoppingItem(id = UUID.randomUUID(), name = "牛乳", 3),
            )
        }.flatten()
    }
}
