package com.lawpavilion.rick_and_morty.character.data.remote.mappers

import com.lawpavilion.rick_and_morty.character.data.remote.responses.LocationResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationMapperTest {
    @Test
    fun toLocationMapsLocationResponseToLocationCorrectly() {
        val response = LocationResponse(
            name = "Law Pavilion HQ",
            url = "https://www.lawpavilion.com/"
        )

        val location = response.toLocation()

        assertEquals(location.name, response.name)
        assertEquals(location.url, response.url)
    }
}