package com.lawpavilion.rick_and_morty.character.data.local.mappers

import com.lawpavilion.rick_and_morty.character.data.local.entities.CharacterEntity
import com.lawpavilion.rick_and_morty.character.domain.models.Character
import com.lawpavilion.rick_and_morty.character.domain.models.CharacterStatus
import com.lawpavilion.rick_and_morty.character.domain.models.Gender
import kotlin.time.Instant

fun CharacterEntity.toCharacter(): Character {
    return Character(
        id = id,
        name = name,
        status = getStatus(status),
        species = species,
        type = type,
        gender = getGender(gender),
        origin = origin.toLocation(),
        location = location.toLocation(),
        imageUrl = imageUrl,
        episodeUrls = episodeUrls,
        url = url,
        createdAt = Instant.fromEpochMilliseconds(createdAt)
    )
}

fun Character.toEntity(expiresAt: Long?): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        status = status.name,
        species = species,
        type = type,
        gender = gender.name,
        origin = origin.toReference(),
        location = location.toReference(),
        imageUrl = imageUrl,
        episodeUrls = episodeUrls,
        url = url,
        createdAt = createdAt.toEpochMilliseconds(),
        expiresAt = expiresAt
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