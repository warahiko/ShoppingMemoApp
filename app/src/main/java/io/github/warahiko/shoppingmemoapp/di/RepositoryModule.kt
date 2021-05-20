package io.github.warahiko.shoppingmemoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.github.warahiko.shoppingmemoapp.data.repository.ShoppingListRepository
import io.github.warahiko.shoppingmemoapp.data.repository.impl.ShoppingListRepositoryImpl

@Suppress("unused")
@Module
@InstallIn(ApplicationComponent::class)
interface RepositoryModule {

    @Binds
    fun bindShoppingListRepository(impl: ShoppingListRepositoryImpl): ShoppingListRepository
}
