package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class CharacterPageInfoMapperTest {

    @Test
    fun toInfoMapsNullUrlsCorrectly() {
        val response = RemoteCharacterTestDefaults.createCharacterPageInfoResponse(
            count = 100,
            pages = 5,
            next = null,
            prev = null
        )

        val info = response.toInfo()

        assertNull(info.next)
        assertNull(info.previous)
    }

    @Test
    fun toInfoMapsNonNullUrlsCorrectly() {
        val response = RemoteCharacterTestDefaults.createCharacterPageInfoResponse(
            count = 100,
            pages = 5,
            next = "https://rickandmortyapi.com/api/character?page=3",
            prev = "https://rickandmortyapi.com/api/character?page=1"
        )

        val info = response.toInfo()

        assertEquals(3, info.next)
        assertEquals(1, info.previous)
    }
}
