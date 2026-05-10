package com.lawpavilion.rick_and_morty.character.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class CharacterPageInfoResponse(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)