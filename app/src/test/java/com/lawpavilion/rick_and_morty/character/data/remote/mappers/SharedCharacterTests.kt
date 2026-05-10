package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterResponse
import com.lawpavilion.rick_and_morty.character.domain.models.Character
import org.junit.Assert.assertEquals

object SharedCharacterTests {
    fun compareCharacter(character: Character, response: CharacterResponse) {
        assertEquals(response.id, character.id)
        assertEquals(response.name, character.name)

        assertEquals(
            response.status.lowercase(),
            character.status.name.lowercase()
        )

        assertEquals(response.species, character.species)
        assertEquals(response.type, character.type)

        assertEquals(
            response.gender.lowercase(),
            character.gender.name.lowercase()
        )

        assertEquals(response.origin.name, character.origin.name)
        assertEquals(response.origin.url, character.origin.url)

        assertEquals(response.location.name, character.location.name)
        assertEquals(response.location.url, character.location.url)

        assertEquals(response.episode.size, character.episodeUrls.size)

        response.episode.forEachIndexed { index, url ->
            assertEquals(url, character.episodeUrls[index])
        }

        assertEquals(response.image, character.imageUrl)
        assertEquals(response.url, character.url)
        assertEquals(response.created, character.createdAt.toString())
    }
}