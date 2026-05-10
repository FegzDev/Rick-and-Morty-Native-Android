package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterPageInfoResponse
import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterResponse
import com.lawpavilion.rick_and_morty.character.data.remote.responses.LocationResponse

object RemoteCharacterTestDefaults {
    fun createDummyCharacterPageInfoResponse(
        count: Int,
        pages: Int,
        next: String?,
        prev: String?
    ): CharacterPageInfoResponse {
        return CharacterPageInfoResponse(count = count, pages = pages, next = next, prev = prev)
    }

    fun createDummyCharacterResponse(status: String, gender: String): CharacterResponse {
        return CharacterResponse(
            id = 1,
            name = "Rick Sanchez",
            status = status,
            species = "Human",
            type = "",
            gender = gender,
            origin = LocationResponse(
                name = "Earth (C-137)",
                url = "https://rickandmortyapi.com/api/location/1"
            ),
            location = LocationResponse(
                name = "Citadel of Ricks",
                url = "https://rickandmortyapi.com/api/location/3"
            ),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episode = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/3",
                "https://rickandmortyapi.com/api/episode/5"
            ),
            url = "https://rickandmortyapi.com/api/character/1",
            created = "2017-11-04T18:48:46.250Z"
        )
    }
}