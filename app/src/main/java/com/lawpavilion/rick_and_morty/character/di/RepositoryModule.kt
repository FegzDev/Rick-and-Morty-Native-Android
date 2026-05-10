package com.lawpavilion.rick_and_morty.character.di

import com.lawpavilion.rick_and_morty.character.data.repositories.CharacterRepositoryImpl
import com.lawpavilion.rick_and_morty.character.domain.repositories.CharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCharacterRepository(impl: CharacterRepositoryImpl): CharacterRepository
}