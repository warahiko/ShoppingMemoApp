package io.github.warahiko.shoppingmemoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.warahiko.shoppingmemoapp.BuildConfig
import io.github.warahiko.shoppingmemoapp.data.network.RetrofitFactory
import io.github.warahiko.shoppingmemoapp.data.network.api.ShoppingListApi
import io.github.warahiko.shoppingmemoapp.data.network.api.TagListApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideNotionRetrofit(): Retrofit = RetrofitFactory.create(BuildConfig.NOTION_BASE_URL) {
        addHeader("Authorization", "Bearer ${BuildConfig.NOTION_TOKEN}")
        addHeader("Notion-Version", BuildConfig.NOTION_VERSION)
    }

    @Singleton
    @Provides
    fun provideShoppingListApi(
        retrofit: Retrofit,
    ): ShoppingListApi = retrofit.create(ShoppingListApi::class.java)

    @Singleton
    @Provides
    fun provideTagListApi(
        retrofit: Retrofit,
    ): TagListApi = retrofit.create(TagListApi::class.java)
}
