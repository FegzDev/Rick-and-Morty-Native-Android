package com.lawpavilion.rick_and_morty.character.data.local.daos

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lawpavilion.rick_and_morty.character.data.local.entities.CharacterEntity
import com.lawpavilion.rick_and_morty.character.data.local.entities.LocationReference
import com.lawpavilion.rick_and_morty.database.AppDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.jvm.java

@RunWith(AndroidJUnit4::class)
class CharacterDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: CharacterDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context = context,
            klass = AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.characterDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    private fun createDummyEntity(id: Int): CharacterEntity {
        return CharacterEntity(
            id = id,
            name = "Character $id",
            status = "Alive",
            species = "Human",
            type = "",
            gender = "Male",
            origin = LocationReference(name = "Earth", url = "url"),
            location = LocationReference(name = "Earth", url = "url"),
            imageUrl = "image_url",
            episodeUrls = listOf("episode_1"),
            url = "character_url",
            createdAt = 1000L,
            expiresAt = 2000L
        )
    }

    @Test
    fun upsetCharactersInsertsCharactersCorrectly() = runTest {
        val characters = (1..5).map(::createDummyEntity)

        val insertedIds = dao.upsertCharacters(characters)

        assertEquals(5, insertedIds.size)

        insertedIds.forEachIndexed { index, id ->
            assertEquals(characters[index].id, id.toInt())
        }
    }

    @Test
    fun upsetCharactersUpdatesCharactersCorrectly() = runTest {
        val characters = (1..5).map(::createDummyEntity)

        dao.upsertCharacters(characters)
        val updatedIds = dao.upsertCharacters(characters)

        assertEquals(5, updatedIds.size)

        updatedIds.forEach { id ->
            assertEquals(-1, id)
        }
    }

    @Test
    fun upsetCharacterInsertsCharacterCorrectly() = runTest {
        val character = createDummyEntity(1)
        val insertedId = dao.upsertCharacter(character)

        assertEquals(1, insertedId)
    }

    @Test
    fun upsetCharacterUpdatesCharacterCorrectly() = runTest {
        val character = createDummyEntity(1)

        dao.upsertCharacter(character)
        val updatedId = dao.upsertCharacter(character)

        assertEquals(-1, updatedId)
    }

    @Test
    fun getCharactersReturnsCorrectSubset() = runTest {
        val characters = (1..5).map(::createDummyEntity)

        dao.upsertCharacters(characters)

        val firstPage = dao.getCharacters(offset = 0, count = 2)

        assertEquals(2, firstPage.size)
        assertEquals(1, firstPage[0].id)
        assertEquals(2, firstPage[1].id)

        val secondPage = dao.getCharacters(offset = 2, count = 2)

        assertEquals(2, secondPage.size)
        assertEquals(3, secondPage[0].id)
        assertEquals(4, secondPage[1].id)
    }

    @Test
    fun getNonExistentCharacterReturnsNull() = runTest {
        val retrieved = dao.getCharacter(999)
        assertNull(retrieved)
    }

    @Test
    fun getExistentCharacterReturnsCorrectly() = runTest {
        val character = createDummyEntity(1)

        dao.upsertCharacter(character)
        val retrievedCharacter = dao.getCharacter(1)

        assertEquals(character, retrievedCharacter)
    }
}
