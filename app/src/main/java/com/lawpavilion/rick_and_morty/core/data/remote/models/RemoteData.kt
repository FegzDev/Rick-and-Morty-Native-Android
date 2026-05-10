package com.lawpavilion.rick_and_morty.core.data.remote.models

data class RemoteData<D>(
    val data: D,
    val cacheMaxAge: Long? = null
)
