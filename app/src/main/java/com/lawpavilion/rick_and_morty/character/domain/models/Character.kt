package com.lawpavilion.rick_and_morty.character.domain.models

import kotlin.time.Instant

data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: Gender,
    val origin: Location,
    val location: Location,
    val imageUrl: String,
    val episodeUrls: List<String>,
    val url: String,
    val createdAt: Instant
)

fun Character.firstName() = name.substringBefore(" ")