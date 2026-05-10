package com.lawpavilion.rick_and_morty.core.data.remote.utils

object RemoteDataDefaults {
    const val BASE_URL = "https://rickandmortyapi.com/api/"
    const val CHARACTER_PAGE_URL = "character"
    const val EPISODE_PAGE_URL = "episode"
    const val PAGE_PARAM_KEY = "page"
    const val NAME_PARAM_KEY = "name"
    const val STATUS_PARAM_KEY = "status"
    const val SPECIES_PARAM_KEY = "species"

    fun getCharacterUrl(id: Int) = "$CHARACTER_PAGE_URL/$id"
    fun getEpisodesUrl(ids: List<Int>) = "$EPISODE_PAGE_URL/${ids.joinToString(",")}"
}