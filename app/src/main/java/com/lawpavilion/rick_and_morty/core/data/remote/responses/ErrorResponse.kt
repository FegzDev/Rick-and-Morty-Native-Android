package com.lawpavilion.rick_and_morty.core.data.remote.responses

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val error: String)