package com.lawpavilion.rick_and_morty.character.domain.models

data class CharacterPage(
    val info: CharacterPageInfo,
    val characters: List<Character>
)