package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterResponse
import com.lawpavilion.rick_and_morty.character.domain.models.Character
import org.junit.Assert.assertEquals

object SharedCharacterTests {
    fun compareCharacter(character: Character, response: CharacterResponse) {
        assertEquals(character.id, response.id)
        assertEquals(character.name, response.name)

        assertEquals(
            character.status.name.lowercase(),
            response.status.lowercase()
        )

        assertEquals(character.species, response.species)
        assertEquals(character.type, response.type)

        assertEquals(
            character.gender.name.lowercase(),
            response.gender.lowercase()
        )

        assertEquals(character.origin.name, response.origin.name)
        assertEquals(character.origin.url, response.origin.url)

        assertEquals(character.location.name, response.location.name)
        assertEquals(character.location.url, response.location.url)

        assertEquals(character.episodeUrls.size, response.episode.size)

        character.episodeUrls.forEachIndexed { index, url ->
            assertEquals(url, response.episode[index])
        }

        assertEquals(character.imageUrl, response.image)
        assertEquals(character.url, response.url)
        assertEquals(character.createdAt.toString(), response.created)
    }
}