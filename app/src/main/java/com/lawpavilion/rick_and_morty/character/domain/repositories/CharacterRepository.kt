package com.lawpavilion.rick_and_morty.character.domain.repositories

import com.lawpavilion.rick_and_morty.character.domain.models.Character
import com.lawpavilion.rick_and_morty.character.domain.models.CharacterPage
import com.lawpavilion.rick_and_morty.core.domain.error_handling.DomainResult

interface CharacterRepository {
    suspend fun getPage(
        index: Int = 1,
        name: String? = null,
        status: String? = null,
        species: String? = null
    ): DomainResult<CharacterPage>

    suspend fun getCharacter(id: Int): DomainResult<Character>
}