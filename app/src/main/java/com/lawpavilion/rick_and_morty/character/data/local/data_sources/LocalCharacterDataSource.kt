package com.lawpavilion.rick_and_morty.character.data.local.data_sources

import com.lawpavilion.rick_and_morty.character.data.local.entities.CharacterEntity

interface LocalCharacterDataSource {
    suspend fun getCharacters(offset: Int, count: Int): List<CharacterEntity>
    suspend fun getCharacter(id: Int): CharacterEntity?
    suspend fun upsertCharacters(characters: List<CharacterEntity>)
    suspend fun upsertCharacter(character: CharacterEntity)
}