package com.lawpavilion.rick_and_morty.character.data.repositories

import com.lawpavilion.rick_and_morty.character.data.local.data_sources.LocalCharacterDataSource
import com.lawpavilion.rick_and_morty.character.data.local.entities.CharacterEntity
import com.lawpavilion.rick_and_morty.character.data.local.entities.LocationReference
import com.lawpavilion.rick_and_morty.character.data.remote.data_sources.RemoteCharacterDataSource
import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterPageInfoResponse
import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterPageResponse
import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterResponse
import com.lawpavilion.rick_and_morty.character.data.remote.responses.LocationResponse
import com.lawpavilion.rick_and_morty.core.data.remote.error_handling.RemoteResult
import com.lawpavilion.rick_and_morty.core.data.remote.models.RemoteData
import com.lawpavilion.rick_and_morty.core.domain.error_handling.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.time.Clock

class CharacterRepositoryImplTest {

    private lateinit var localDataSource: LocalCharacterDataSource
    private lateinit var remoteDataSource: RemoteCharacterDataSource
    private lateinit var repository: CharacterRepositoryImpl

    @Before
    fun setup() {
        localDataSource = mockk(relaxed = true)
        remoteDataSource = mockk(relaxed = true)
        repository = CharacterRepositoryImpl(localDataSource, remoteDataSource)
    }

    private fun createDummyEntity(id: Int, expiresAt: Long?): CharacterEntity {
        return CharacterEntity(
            id = id,
            name = "Test",
            status = "Alive",
            species = "Human",
            type = "Local",
            gender = "Male",
            origin = LocationReference("Earth", "url"),
            location = LocationReference("Earth", "url"),
            imageUrl = "url",
            episodeUrls = emptyList(),
            url = "url",
            createdAt = 1000L,
            expiresAt = expiresAt
        )
    }

    private fun createDummyResponse(id: Int): CharacterResponse {
        return CharacterResponse(
            id = id,
            name = "Test",
            status = "Alive",
            species = "Human",
            type = "Remote",
            gender = "Male",
            origin = LocationResponse("Earth", "url"),
            location = LocationResponse("Earth", "url"),
            image = "url",
            episode = emptyList(),
            url = "url",
            created = "2023-01-01T00:00:00.000Z"
        )
    }

    @Test
    fun getPageWithParamsSkipsLocalCacheQueryAndQueriesRemote() = runTest {
        val remoteResponse = CharacterPageResponse(
            info = CharacterPageInfoResponse(count = 1, pages = 1, next = null, prev = null),
            results = listOf(createDummyResponse(1))
        )

        val remoteData = RemoteData(remoteResponse)

        val remoteResult: RemoteResult<CharacterPageResponse> = Result.Success(remoteData)

        coEvery {
            remoteDataSource.getPage(index = 1, name = "Rick", status = null, species = null)
        } returns remoteResult

        repository.getPage(index = 1, name = "Rick", status = null, species = null)

        coVerify(exactly = 0) {
            localDataSource.getCharacters(offset = any(), count = any())
        }

        coVerify(exactly = 1) {
            remoteDataSource.getPage(
                index = 1,
                name = "Rick",
                status = null,
                species = null
            )
        }
    }

    @Test
    fun getPageWithNoParamsQueriesLocalCacheFirst() = runTest {
        val expiresAt = Clock.System.now().toEpochMilliseconds() + 100000L
        val localCharacters = listOf(createDummyEntity(1, expiresAt))

        coEvery {
            localDataSource.getCharacters(offset = any(), count = any())
        } returns localCharacters

        repository.getPage(index = 1, name = null, status = null, species = null)

        coVerify(exactly = 1) {
            localDataSource.getCharacters(0, 21)
        }

        coVerify(exactly = 0) {
            remoteDataSource.getPage(
                index = any(),
                name = any(),
                status = any(),
                species = any()
            )
        }
    }

    @Test
    fun getPageIgnoresLocalCacheIfExpired() = runTest {
        val expiresAt = Clock.System.now().toEpochMilliseconds() - 100000L
        val localCharacters = listOf(createDummyEntity(1, expiresAt))

        coEvery {
            localDataSource.getCharacters(offset = any(), count = any())
        } returns localCharacters

        val remoteResponse = CharacterPageResponse(
            info = CharacterPageInfoResponse(count = 1, pages = 1, next = null, prev = null),
            results = listOf(createDummyResponse(2))
        )

        val remoteData = RemoteData(remoteResponse)

        val remoteResult: RemoteResult<CharacterPageResponse> = Result.Success(remoteData)

        coEvery {
            remoteDataSource.getPage(
                index = any(),
                name = any(),
                status = any(),
                species = any()
            )
        } returns remoteResult

        val result = repository.getPage(index = 1, name = null, status = null, species = null)

        coVerify(exactly = 1) {
            localDataSource.getCharacters(0, 21)
        }

        coVerify(exactly = 1) {
            remoteDataSource.getPage(
                index = any(),
                name = any(),
                status = any(),
                species = any()
            )
        }

        assertTrue(result is Result.Success)

        val successResult = result as Result.Success

        assertEquals(1, successResult.data.characters.size)
        assertNotEquals(1, successResult.data.characters.first().id)
        assertEquals(2, successResult.data.characters.first().id)
    }

    @Test
    fun getCharacterQueriesLocalCacheFirst() = runTest {
        val expiresAt = Clock.System.now().toEpochMilliseconds() + 100000L
        val localCharacter = createDummyEntity(1, expiresAt)

        coEvery { localDataSource.getCharacter(1) } returns localCharacter

        repository.getCharacter(1)

        coVerify(exactly = 1) { localDataSource.getCharacter(1) }
        coVerify(exactly = 0) { remoteDataSource.getCharacter(any()) }
    }

    @Test
    fun getCharacterIgnoresLocalCacheIfExpired() = runTest {
        val expiresAt = Clock.System.now().toEpochMilliseconds() - 100000L
        val localCharacter = createDummyEntity(1, expiresAt)

        coEvery { localDataSource.getCharacter(1) } returns localCharacter

        val remoteResponse = createDummyResponse(1)
        val remoteData = RemoteData(remoteResponse)
        val remoteResult: RemoteResult<CharacterResponse> = Result.Success(remoteData)

        coEvery { remoteDataSource.getCharacter(1) } returns remoteResult

        val result = repository.getCharacter(1)

        coVerify(exactly = 1) { localDataSource.getCharacter(1) }
        coVerify(exactly = 1) { remoteDataSource.getCharacter(1) }

        assertTrue(result is Result.Success)

        val successResult = result as Result.Success

        assertNotEquals(localCharacter.type, successResult.data.type)
        assertEquals(remoteResponse.type, successResult.data.type)
    }
}
