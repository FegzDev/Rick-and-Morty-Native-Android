package com.lawpavilion.rick_and_morty.core.domain.error_handling

sealed interface DomainError {
    data object NoNetwork : DomainError
    data object Serialization : DomainError
    data object Redirection : DomainError
    data class NotFound(val message: String? = null) : DomainError
    data class RequestRejected(val message: String? = null) : DomainError
    data object ServerError : DomainError
    data object Unknown : DomainError
}