package io.github.warahiko.shoppingmemoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.warahiko.shoppingmemoapp.usecase.AddShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.ArchiveShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.ChangeShoppingItemIsDoneUseCase
import io.github.warahiko.shoppingmemoapp.usecase.DeleteShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.EditShoppingItemUseCase
import io.github.warahiko.shoppingmemoapp.usecase.FetchShoppingListUseCase
import io.github.warahiko.shoppingmemoapp.usecase.impl.AddShoppingItemUseCaseImpl
import io.github.warahiko.shoppingmemoapp.usecase.impl.ArchiveShoppingItemUseCaseImpl
import io.github.warahiko.shoppingmemoapp.usecase.impl.ChangeShoppingItemIsDoneUseCaseImpl
import io.github.warahiko.shoppingmemoapp.usecase.impl.DeleteShoppingItemUseCaseImpl
import io.github.warahiko.shoppingmemoapp.usecase.impl.EditShoppingItemUseCaseImpl
import io.github.warahiko.shoppingmemoapp.usecase.impl.FetchShoppingListUseCaseImpl

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindFetchShoppingListUseCase(impl: FetchShoppingListUseCaseImpl): FetchShoppingListUseCase

    @Binds
    fun bindAddShoppingItemUseCase(impl: AddShoppingItemUseCaseImpl): AddShoppingItemUseCase

    @Binds
    fun bindUpdateShoppingItemUseCase(impl: ChangeShoppingItemIsDoneUseCaseImpl):
        ChangeShoppingItemIsDoneUseCase

    @Binds
    fun bindEditShoppingItemUseCase(impl: EditShoppingItemUseCaseImpl): EditShoppingItemUseCase

    @Binds
    fun bindArchiveShoppingItem(impl: ArchiveShoppingItemUseCaseImpl): ArchiveShoppingItemUseCase

    @Binds
    fun bindDeleteShoppingItem(impl: DeleteShoppingItemUseCaseImpl): DeleteShoppingItemUseCase
}
