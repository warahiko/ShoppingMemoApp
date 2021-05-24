package io.github.warahiko.shoppingmemoapp.usecase.impl

import io.github.warahiko.shoppingmemoapp.data.network.model.Filter
import io.github.warahiko.shoppingmemoapp.data.network.model.FilterProperty
import io.github.warahiko.shoppingmemoapp.data.network.model.FilterSelect
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.model.ShoppingItem
import io.github.warahiko.shoppingmemoapp.model.Status
import io.github.warahiko.shoppingmemoapp.usecase.FetchShoppingListUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchShoppingListUseCaseImpl @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository,
) : FetchShoppingListUseCase {
    override suspend fun invoke(): Flow<List<ShoppingItem>> =
        shoppingListRepository.getShoppingList(
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
