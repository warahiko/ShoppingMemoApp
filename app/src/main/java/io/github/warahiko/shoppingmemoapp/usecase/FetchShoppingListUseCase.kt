package io.github.warahiko.shoppingmemoapp.usecase

import io.github.warahiko.shoppingmemoapp.data.network.model.Filter
import io.github.warahiko.shoppingmemoapp.data.network.model.FilterProperty
import io.github.warahiko.shoppingmemoapp.data.network.model.FilterSelect
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.model.Status
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchShoppingListUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) {
    suspend operator fun invoke(): Flow<List<ShoppingItem>> {
        return shoppingListRepository.getShoppingList(
            filter = Filter(
                or = listOf(
                    FilterProperty(
                        property = "Status",
                        select = FilterSelect(equals = Status.NEW.text),
                    ),
                    FilterProperty(
                        property = "Status",
                        select = FilterSelect(equals = Status.DONE.text),
                    ),
                ),
            ),
        )
    }
}
