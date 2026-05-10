package com.lawpavilion.rick_and_morty.character.data.remote.data_sources

import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterPageResponse
import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterResponse
import com.lawpavilion.rick_and_morty.core.data.remote.error_handling.RemoteResult

interface RemoteCharacterDataSource {
    suspend fun getPage(
        index: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null
    ): RemoteResult<CharacterPageResponse>

    suspend fun getCharacter(id: Int): RemoteResult<CharacterResponse>
}