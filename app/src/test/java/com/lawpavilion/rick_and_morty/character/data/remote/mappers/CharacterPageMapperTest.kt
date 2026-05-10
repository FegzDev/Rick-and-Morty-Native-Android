package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterPageResponse
import com.lawpavilion.rick_and_morty.character.domain.models.CharacterStatus
import com.lawpavilion.rick_and_morty.character.domain.models.Gender
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CharacterPageMapperTest {

    @Test
    fun toPageMapsCharacterPageResponseToCharacterPage() {
        val infoResponse = RemoteCharacterTestDefaults.createDummyCharacterPageInfoResponse(
            count = 100,
            pages = 5,
            next = "https://rickandmortyapi.com/api/character?page=2",
            prev = null
        )

        val response = CharacterPageResponse(
            info = infoResponse,
            results = listOf(
                RemoteCharacterTestDefaults.createDummyCharacterResponse(
                    status = CharacterStatus.ALIVE.name,
                    gender = Gender.MALE.name
                ),
                RemoteCharacterTestDefaults.createDummyCharacterResponse(
                    status = CharacterStatus.DEAD.name,
                    gender = Gender.GENDERLESS.name
                )
            )
        )

        val page = response.toPage()

        assertEquals(2, page.info.next)
        assertNull(page.info.previous)

        assertEquals(response.results.size, page.characters.size)

        page.characters.forEachIndexed { index, character ->
            SharedCharacterTests.compareCharacter(character, response.results[index])
        }
    }
}
