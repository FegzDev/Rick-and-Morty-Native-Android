package com.lawpavilion.rick_and_morty.character.data.remote.data_sources

import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterPageResponse
import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterResponse
import com.lawpavilion.rick_and_morty.core.data.remote.error_handling.RemoteResult
import com.lawpavilion.rick_and_morty.core.data.remote.error_handling.safeGet
import com.lawpavilion.rick_and_morty.core.data.remote.utils.RemoteDataDefaults
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import jakarta.inject.Inject

class KtorCharacterDataSource @Inject constructor(private val client: HttpClient) :
    RemoteCharacterDataSource {
    override suspend fun getPage(
        index: Int,
        name: String?,
        status: String?,
        species: String?
    ): RemoteResult<CharacterPageResponse> {
        return client.safeGet(RemoteDataDefaults.CHARACTER_PAGE_URL) {
            parameter(key = RemoteDataDefaults.PAGE_PARAM_KEY, value = index)

            if (!name.isNullOrBlank()) {
                parameter(key = RemoteDataDefaults.NAME_PARAM_KEY, value = name)
            }

            if (!status.isNullOrBlank()) {
                parameter(key = RemoteDataDefaults.STATUS_PARAM_KEY, value = status)
            }

            if (!species.isNullOrBlank()) {
                parameter(key = RemoteDataDefaults.SPECIES_PARAM_KEY, value = species)
            }
        }
    }

    override suspend fun getCharacter(id: Int): RemoteResult<CharacterResponse> {
        return client.safeGet(RemoteDataDefaults.getCharacterUrl(id))
    }
}