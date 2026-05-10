package com.lawpavilion.rick_and_morty.character.data.local.mappers

import com.lawpavilion.rick_and_morty.character.data.local.entities.LocationReference
import com.lawpavilion.rick_and_morty.character.domain.models.Location
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationMapperTest {
    @Test
    fun toLocationMapsLocationReferenceToLocation() {
        val reference = LocationReference("Earth", "url")
        val location = reference.toLocation()

        assertEquals(reference.name, location.name)
        assertEquals(reference.url, location.url)
    }

    @Test
    fun toReferenceMapsLocationToLocationReference() {
        val location = Location("Earth", "url")
        val reference = location.toReference()

        assertEquals(location.name, reference.name)
        assertEquals(location.url, reference.url)
    }
}