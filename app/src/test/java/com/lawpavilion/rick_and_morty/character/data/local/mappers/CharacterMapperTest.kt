package com.lawpavilion.rick_and_morty.character.data.local.mappers

import com.lawpavilion.rick_and_morty.character.data.local.entities.CharacterEntity
import com.lawpavilion.rick_and_morty.character.data.local.entities.LocationReference
import com.lawpavilion.rick_and_morty.character.domain.models.Character
import com.lawpavilion.rick_and_morty.character.domain.models.CharacterStatus
import com.lawpavilion.rick_and_morty.character.domain.models.Gender
import com.lawpavilion.rick_and_morty.character.domain.models.Location
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.Instant

class CharacterMapperTest {

    @Test
    fun toCharacterMapsCharacterEntityToCharacter() {
        val entity = createDummyEntity(
            status = CharacterStatus.ALIVE.name,
            gender = Gender.MALE.name
        )

        val character = entity.toCharacter()

        assertEquals(entity.id, character.id)
        assertEquals(entity.name, character.name)

        assertEquals(
            entity.status.lowercase(),
            character.status.name.lowercase()
        )

        assertEquals(entity.species, character.species)
        assertEquals(entity.type, character.type)

        assertEquals(
            entity.gender.lowercase(),
            character.gender.name.lowercase()
        )

        assertEquals(entity.origin.name, character.origin.name)
        assertEquals(entity.origin.url, character.origin.url)

        assertEquals(entity.location.name, character.location.name)
        assertEquals(entity.location.url, character.location.url)

        assertEquals(entity.episodeUrls.size, character.episodeUrls.size)

        entity.episodeUrls.forEachIndexed { index, url ->
            assertEquals(url, character.episodeUrls[index])
        }

        assertEquals(entity.imageUrl, character.imageUrl)
        assertEquals(entity.url, character.url)
        assertEquals(entity.createdAt, character.createdAt.toEpochMilliseconds())
    }

    @Test
    fun toEntityMapsCharacterToCharacterEntity() {
        val character = Character(
            id = 1,
            name = "Rick Sanchez",
            status = CharacterStatus.ALIVE,
            species = "Human",
            type = "",
            gender = Gender.MALE,
            origin = Location(
                name = "Earth (C-137)",
                url = "https://rickandmortyapi.com/api/location/1"
            ),
            location = Location(
                name = "Citadel of Ricks",
                url = "https://rickandmortyapi.com/api/location/3"
            ),
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episodeUrls = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/3",
                "https://rickandmortyapi.com/api/episode/5"
            ),
            url = "https://rickandmortyapi.com/api/character/1",
            createdAt = Instant.fromEpochMilliseconds(1509821326250)
        )

        val expiresAt = 1517597326250

        val entity = character.toEntity(expiresAt)

        assertEquals(character.id, entity.id)
        assertEquals(character.name, entity.name)

        assertEquals(
            character.status.name.lowercase(),
            entity.status.lowercase()
        )

        assertEquals(character.species, entity.species)
        assertEquals(character.type, entity.type)

        assertEquals(
            character.gender.name.lowercase(),
            entity.gender.lowercase()
        )

        assertEquals(character.origin.name, entity.origin.name)
        assertEquals(character.origin.url, entity.origin.url)

        assertEquals(character.location.name, entity.location.name)
        assertEquals(character.location.url, entity.location.url)

        assertEquals(character.episodeUrls.size, entity.episodeUrls.size)

        character.episodeUrls.forEachIndexed { index, url ->
            assertEquals(url, entity.episodeUrls[index])
        }

        assertEquals(character.imageUrl, entity.imageUrl)
        assertEquals(character.url, entity.url)

        assertEquals(
            character.createdAt,
            Instant.fromEpochMilliseconds(entity.createdAt)
        )

        assertEquals(expiresAt, entity.expiresAt)
    }

    @Test
    fun getStatusMapsEntityStatusToCharacterStatus() {
        CharacterStatus.entries.forEach { status ->
            val entity = createDummyEntity(
                status = status.name,
                gender = Gender.MALE.name
            )

            val character = entity.toCharacter()

            assertEquals(status, character.status)
        }
    }

    @Test
    fun getGenderMapsEntityGenderToGender() {
        Gender.entries.forEach { gender ->
            val entity = createDummyEntity(
                status = CharacterStatus.ALIVE.name,
                gender = gender.name
            )

            val character = entity.toCharacter()

            assertEquals(gender, character.gender)
        }
    }

    fun createDummyEntity(status: String, gender: String): CharacterEntity {
        return CharacterEntity(
            id = 1,
            name = "Rick Sanchez",
            status = status,
            species = "Human",
            type = "",
            gender = gender,
            origin = LocationReference(
                name = "Earth (C-137)",
                url = "https://rickandmortyapi.com/api/location/1"
            ),
            location = LocationReference(
                name = "Citadel of Ricks",
                url = "https://rickandmortyapi.com/api/location/3"
            ),
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episodeUrls = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/3",
                "https://rickandmortyapi.com/api/episode/5"
            ),
            url = "https://rickandmortyapi.com/api/character/1",
            createdAt = 1509821326250,
            expiresAt = 1517597326250
        )
    }
}
