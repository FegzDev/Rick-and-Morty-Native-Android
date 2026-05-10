package com.lawpavilion.rick_and_morty.character.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class LocationResponse(
    val name: String,
    val url: String
)