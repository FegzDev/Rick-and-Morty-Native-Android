package com.lawpavilion.rick_and_morty.core.data.remote.error_handling

import com.lawpavilion.rick_and_morty.core.data.remote.responses.ErrorResponse

sealed interface RemoteError {
    data object Network : RemoteError
    data object Serialization : RemoteError
    data object Redirection : RemoteError
    data class ClientSide(
        val code: Int,
        val error: ErrorResponse? = null
    ) : RemoteError

    data object ServerSide : RemoteError
    data object Unknown : RemoteError
}