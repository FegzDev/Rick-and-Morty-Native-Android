package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterPageInfoResponse
import com.lawpavilion.rick_and_morty.character.domain.models.CharacterPageInfo
import com.lawpavilion.rick_and_morty.core.data.remote.utils.RemoteDataDefaults

fun CharacterPageInfoResponse.toInfo(): CharacterPageInfo {
    return CharacterPageInfo(
        next = next?.getPage(),
        previous = prev?.getPage()
    )
}

private fun String.getPage(): Int? {
    return substringAfterLast("${RemoteDataDefaults.PAGE_PARAM_KEY}=")
        .takeWhile { it.isDigit() }
        .toIntOrNull()
}
