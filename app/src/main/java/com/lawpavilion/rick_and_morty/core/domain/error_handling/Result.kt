package com.lawpavilion.rick_and_morty.core.domain.error_handling

sealed interface Result<out D, out E> {
    data class Success<out D, out E>(val data: D) : Result<D, E>
    data class Failure<out D, out E>(val error: E) : Result<D, E>
}

inline fun <D, E> Result<D, E>.onSuccess(block: (D) -> Unit): Result<D, E> {
    if (this is Result.Success) block(data)
    return this
}