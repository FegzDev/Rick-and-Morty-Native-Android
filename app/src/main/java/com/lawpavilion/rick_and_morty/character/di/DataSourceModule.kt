package com.lawpavilion.rick_and_morty.character.di

import com.lawpavilion.rick_and_morty.character.data.local.data_sources.LocalCharacterDataSource
import com.lawpavilion.rick_and_morty.character.data.local.data_sources.RoomCharacterDataSource
import com.lawpavilion.rick_and_morty.character.data.remote.data_sources.KtorCharacterDataSource
import com.lawpavilion.rick_and_morty.character.data.remote.data_sources.RemoteCharacterDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindLocalCharacterDataSource(dataSource: RoomCharacterDataSource): LocalCharacterDataSource

    @Binds
    abstract fun bindRemoteCharacterDataSource(dataSource: KtorCharacterDataSource): RemoteCharacterDataSource
}