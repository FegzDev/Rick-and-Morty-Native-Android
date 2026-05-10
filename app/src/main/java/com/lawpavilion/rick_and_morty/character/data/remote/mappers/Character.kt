package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import com.lawpavilion.rick_and_morty.character.data.remote.responses.CharacterResponse
import com.lawpavilion.rick_and_morty.character.domain.models.Character
import com.lawpavilion.rick_and_morty.character.domain.models.CharacterStatus
import com.lawpavilion.rick_and_morty.character.domain.models.Gender
import kotlin.time.Instant

fun CharacterResponse.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        status = getStatus(status),
        species = species,
        type = type,
        gender = getGender(gender),
        origin = origin.toLocation(),
        location = location.toLocation(),
        imageUrl = image,
        episodeUrls = episode,
        url = url,
        createdAt = Instant.parse(created)
    )
}

private fun getStatus(value: String): CharacterStatus {
    return when (value.lowercase()) {
        CharacterStatus.ALIVE.name.lowercase() -> CharacterStatus.ALIVE
        CharacterStatus.DEAD.name.lowercase() -> CharacterStatus.DEAD
        else -> CharacterStatus.UNKNOWN
    }
}

private fun getGender(value: String): Gender {
    return when (value.lowercase()) {
        Gender.MALE.name.lowercase() -> Gender.MALE
        Gender.FEMALE.name.lowercase() -> Gender.FEMALE
        Gender.GENDERLESS.name.lowercase() -> Gender.GENDERLESS
        else -> Gender.UNKNOWN
    }
}