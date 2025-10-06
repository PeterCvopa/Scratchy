package com.cvopa.peter.network.di

import com.cvopa.peter.network.ActivationServiceDataSource
import com.cvopa.peter.network.ActivationServiceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceLayerModule {

    @Binds
    abstract fun bindActivationServiceDataSource(
        activationServiceDataSource: ActivationServiceDataSourceImpl
    ): ActivationServiceDataSource

}
