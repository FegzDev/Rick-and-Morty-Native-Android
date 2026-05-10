package com.lawpavilion.rick_and_morty.character.data.local.data_sources

import com.lawpavilion.rick_and_morty.character.data.local.daos.CharacterDao
import com.lawpavilion.rick_and_morty.character.data.local.entities.CharacterEntity
import jakarta.inject.Inject

class RoomCharacterDataSource @Inject constructor(private val dao: CharacterDao) :
    LocalCharacterDataSource {
    override suspend fun getCharacters(
        offset: Int,
        count: Int
    ): List<CharacterEntity> {
        return dao.getCharacters(offset = offset, count = count)
    }

    override suspend fun getCharacter(id: Int): CharacterEntity? = dao.getCharacter(id)

    override suspend fun upsertCharacters(characters: List<CharacterEntity>) {
        dao.upsertCharacters(characters)
    }

    override suspend fun upsertCharacter(character: CharacterEntity) {
        dao.upsertCharacter(character)
    }
}