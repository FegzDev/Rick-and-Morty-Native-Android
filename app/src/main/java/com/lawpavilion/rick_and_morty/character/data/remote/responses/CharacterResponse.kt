package com.lawpavilion.rick_and_morty.character.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationResponse,
    val location: LocationResponse,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)