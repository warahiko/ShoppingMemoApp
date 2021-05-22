package io.github.warahiko.shoppingmemoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.warahiko.shoppingmemoapp.usecase.AddShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.FetchShoppingListUseCase
import io.github.warahiko.shoppingmemoapp.usecase.impl.AddShoppingItemUseCaseImpl
import io.github.warahiko.shoppingmemoapp.usecase.impl.FetchShoppingListUseCaseImpl

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindFetchShoppingListUseCase(impl: FetchShoppingListUseCaseImpl): FetchShoppingListUseCase

    @Binds
    fun bindAddShoppingItemUseCase(impl: AddShoppingItemUseCaseImpl): AddShoppingItemUseCase
}
