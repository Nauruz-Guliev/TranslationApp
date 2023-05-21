package ru.kpfu.itis.gnt.translationapitest.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.kpfu.itis.gnt.translationapitest.di.qualifiers.IoDispatcher

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {
    @Provides
    @IoDispatcher
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}
