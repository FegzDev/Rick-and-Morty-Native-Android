package com.lawpavilion.rick_and_morty.character.di

import com.lawpavilion.rick_and_morty.character.data.local.daos.CharacterDao
import com.lawpavilion.rick_and_morty.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideCharacterDao(database: AppDatabase): CharacterDao = database.characterDao()
}