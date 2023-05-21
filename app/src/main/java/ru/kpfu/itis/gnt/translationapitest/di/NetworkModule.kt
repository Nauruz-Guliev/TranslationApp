package ru.kpfu.itis.gnt.translationapitest.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import ru.kpfu.itis.gnt.translationapitest.BuildConfig
import ru.kpfu.itis.gnt.translationapitest.data.remote.TranslationApi
import ru.kpfu.itis.gnt.translationapitest.data.remote.interceptor.ApiKeyInterceptor
import ru.kpfu.itis.gnt.translationapitest.di.qualifiers.BaseUrlString
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @BaseUrlString
    fun provideBaseUrl(): String {
        return BuildConfig.API_URL
    }

    @Provides
    fun provideClient(
        apiKeyInterceptor: ApiKeyInterceptor,
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        @BaseUrlString baseUrlString: String,
        moshiConverterFactory: MoshiConverterFactory,
        client: OkHttpClient
    ): TranslationApi {
        return Retrofit.Builder()
            .baseUrl(baseUrlString)
            .client(client)
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
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        }
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
}
