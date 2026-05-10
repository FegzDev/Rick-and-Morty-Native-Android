package com.lawpavilion.rick_and_morty.character.data.local.mappers

import com.lawpavilion.rick_and_morty.character.data.local.entities.LocationReference
import com.lawpavilion.rick_and_morty.character.domain.models.Location

fun LocationReference.toLocation() = Location(name = name, url = url)

fun Location.toReference() = LocationReference(name = name, url = url)