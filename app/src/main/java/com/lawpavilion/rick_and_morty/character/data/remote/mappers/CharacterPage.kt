package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterPageResponse
import com.lawpavilion.rick_and_morty.character.domain.models.CharacterPage

fun CharacterPageResponse.toPage(): CharacterPage {
    return CharacterPage(
        info = info.toInfo(),
        characters = results.map { it.toCharacter() }
    )
}