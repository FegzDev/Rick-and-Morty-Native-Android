package com.lawpavilion.rick_and_morty.character.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class CharacterPageResponse(
    val info: CharacterPageInfoResponse,
    val results: List<CharacterResponse>
)