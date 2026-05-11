package com.lawpavilion.rick_and_morty.network.di

import com.lawpavilion.rick_and_morty.network.data.ConnectivityManagerNetworkMonitor
import com.lawpavilion.rick_and_morty.network.domain.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkMonitorModule {
    @Binds
    @Singleton
    abstract fun bindNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor
    ): NetworkMonitor
}