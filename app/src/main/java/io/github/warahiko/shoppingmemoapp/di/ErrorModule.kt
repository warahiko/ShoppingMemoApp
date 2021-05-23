package io.github.warahiko.shoppingmemoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.warahiko.shoppingmemoapp.error.LaunchSafe
import io.github.warahiko.shoppingmemoapp.error.impl.LaunchSafeImpl

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
interface ErrorModule {

    @Binds
    fun bindLaunchSafe(impl: LaunchSafeImpl): LaunchSafe
}
