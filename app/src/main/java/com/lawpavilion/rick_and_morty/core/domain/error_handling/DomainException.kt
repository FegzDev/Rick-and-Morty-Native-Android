package com.lawpavilion.rick_and_morty.core.domain.error_handling

data class DomainException(val error: DomainError) : Exception()