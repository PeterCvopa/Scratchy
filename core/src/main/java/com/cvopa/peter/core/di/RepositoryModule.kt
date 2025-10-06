package com.cvopa.peter.core.di

import com.cvopa.peter.core.ScratchRepository
import com.cvopa.peter.core.ScratchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCodeRepository(
        codeRepository: ScratchRepositoryImpl
    ): ScratchRepository
}
