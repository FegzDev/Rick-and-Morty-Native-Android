package com.lawpavilion.rick_and_morty.character.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.lawpavilion.rick_and_morty.character.data.local.entities.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters ORDER BY id ASC LIMIT :count OFFSET :offset")
    suspend fun getCharacters(offset: Int, count: Int): List<CharacterEntity>

    @Query("SELECT * FROM characters WHERE id = :id LIMIT 1")
    suspend fun getCharacter(id: Int): CharacterEntity?

    @Upsert
    suspend fun upsertCharacters(characters: List<CharacterEntity>): List<Long>

    @Upsert
    suspend fun upsertCharacter(character: CharacterEntity): Long
}