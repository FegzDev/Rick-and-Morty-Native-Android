package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import com.lawpavilion.rick_and_morty.core.data.remote.error_handling.RemoteResult
import com.lawpavilion.rick_and_morty.core.domain.error_handling.DomainResult
import com.lawpavilion.rick_and_morty.core.domain.error_handling.Result
import com.lawpavilion.rick_and_morty.core.data.remote.models.RemoteData

inline fun <R, D> RemoteResult<R>.toDomainResult(transform: (RemoteData<R>) -> D): DomainResult<D> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Failure -> Result.Failure(error.toDomainError())
    }
}