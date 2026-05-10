package com.lawpavilion.rick_and_morty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lawpavilion.rick_and_morty.character.data.local.daos.CharacterDao
import com.lawpavilion.rick_and_morty.character.data.local.entities.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}