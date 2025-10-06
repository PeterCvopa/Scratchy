package com.cvopa.peter.scratchy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {
    @Singleton
    @Provides
    @ApplicationScope
    fun provideApplicationScope(
        exceptionHandler: CoroutineExceptionHandler,
    ): CoroutineScope {
        return CoroutineScope(SupervisorJob() + exceptionHandler)
    }
}

@Retention(AnnotationRetention.BINARY) @Qualifier annotation class ApplicationScope

@Retention(AnnotationRetention.BINARY) @Qualifier annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY) @Qualifier annotation class DefaultDispatcher
