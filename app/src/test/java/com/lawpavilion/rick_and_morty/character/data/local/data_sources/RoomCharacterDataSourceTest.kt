package com.lawpavilion.rick_and_morty.character.data.local.data_sources

import com.lawpavilion.rick_and_morty.character.data.local.daos.CharacterDao
import com.lawpavilion.rick_and_morty.character.data.local.entities.CharacterEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RoomCharacterDataSourceTest {

    private lateinit var dao: CharacterDao
    private lateinit var dataSource: RoomCharacterDataSource

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        dataSource = RoomCharacterDataSource(dao)
    }

    @Test
    fun getCharactersDelegatesToDao() = runTest {
        val mockCharacters = listOf<CharacterEntity>(mockk(), mockk())
        coEvery { dao.getCharacters(0, 20) } returns mockCharacters

        val result = dataSource.getCharacters(0, 20)
        coVerify(exactly = 1) { dao.getCharacters(0, 20) }

        assertEquals(mockCharacters, result)
    }

    @Test
    fun getCharacterDelegatesToDao() = runTest {
        val mockCharacter = mockk<CharacterEntity>()
        coEvery { dao.getCharacter(1) } returns mockCharacter

        val result = dataSource.getCharacter(1)
        coVerify(exactly = 1) { dao.getCharacter(1) }

        assertEquals(mockCharacter, result)
    }

    @Test
    fun upsertCharactersDelegatesToDao() = runTest {
        val mockCharacters = listOf<CharacterEntity>(mockk(), mockk())
        dataSource.upsertCharacters(mockCharacters)

        coVerify(exactly = 1) { dao.upsertCharacters(mockCharacters) }
    }

    @Test
    fun upsertCharacterDelegatesToDao() = runTest {
        val mockCharacter = mockk<CharacterEntity>()
        dataSource.upsertCharacter(mockCharacter)
        
        coVerify(exactly = 1) { dao.upsertCharacter(mockCharacter) }
    }
}
