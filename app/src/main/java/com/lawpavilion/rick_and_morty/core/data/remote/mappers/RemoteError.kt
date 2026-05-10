package com.lawpavilion.rick_and_morty.core.data.remote.mappers

import com.lawpavilion.rick_and_morty.core.data.remote.error_handling.RemoteError
import com.lawpavilion.rick_and_morty.core.domain.error_handling.DomainError

fun RemoteError.toDomainError(): DomainError {
    return when (this) {
        is RemoteError.Network -> DomainError.NoNetwork
        is RemoteError.Serialization -> DomainError.Serialization
        is RemoteError.Redirection -> DomainError.Redirection
        is RemoteError.ClientSide -> {
            if (code == 404) {
                DomainError.NotFound(error?.error)
            } else {
                DomainError.RequestRejected(error?.error)
            }
        }

        is RemoteError.ServerSide -> DomainError.ServerError
        is RemoteError.Unknown -> DomainError.Unknown
    }
}