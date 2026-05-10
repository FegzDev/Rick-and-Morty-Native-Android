package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import com.lawpavilion.rick_and_morty.character.data.remote.responses.LocationResponse
import com.lawpavilion.rick_and_morty.character.domain.models.Location

fun LocationResponse.toLocation() = Location(name = name, url = url)