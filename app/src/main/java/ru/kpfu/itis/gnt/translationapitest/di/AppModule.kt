package ru.kpfu.itis.gnt.translationapitest.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.kpfu.itis.gnt.translationapitest.data.remote.TranslationApi
import ru.kpfu.itis.gnt.translationapitest.data.remote.TranslationApi.Companion.BASE_URL
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @BaseUrlString
    fun provideBaseUrl(): String = BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrlString baseUrlString: String,
        moshiConverterFactory: MoshiConverterFactory
    ): TranslationApi {
        return Retrofit.Builder()
            .baseUrl(baseUrlString)
            .addConverterFactory(moshiConverterFactory)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideJsonAdapterFactory(): KotlinJsonAdapterFactory {
        return KotlinJsonAdapterFactory()
    }

    @Provides
    @Singleton
    fun provideMoshi(kotlinJsonAdapterFactory: KotlinJsonAdapterFactory): Moshi {
        return Moshi.Builder()
            .add(kotlinJsonAdapterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO

}
