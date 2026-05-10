package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import com.lawpavilion.rick_and_morty.character.domain.models.CharacterStatus
import com.lawpavilion.rick_and_morty.character.domain.models.Gender
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterMapperTest {

    @Test
    fun toCharacterMapsCharacterResponseToCharacterCorrectly() {
        val response = RemoteCharacterTestDefaults.createCharacterResponse(
            status = CharacterStatus.ALIVE.name,
            gender = Gender.MALE.name
        )

        val character = response.toCharacter()

        SharedCharacterTests.compareCharacter(
            character = character,
            response = response
        )
    }

    @Test
    fun getStatusMapsStatusResponseToCharacterStatusCorrectly() {
        CharacterStatus.entries.forEach { status ->
            val response = RemoteCharacterTestDefaults.createCharacterResponse(
                status = status.name,
                gender = Gender.MALE.name
            )

            val character = response.toCharacter()

            assertEquals(character.status, status)
        }
    }

    @Test
    fun getGenderMapsGenderResponseToGenderCorrectly() {
        Gender.entries.forEach { gender ->
            val response = RemoteCharacterTestDefaults.createCharacterResponse(
                status = CharacterStatus.ALIVE.name,
                gender = gender.name
            )

            val character = response.toCharacter()

            assertEquals(character.gender, gender)
        }
    }
}
