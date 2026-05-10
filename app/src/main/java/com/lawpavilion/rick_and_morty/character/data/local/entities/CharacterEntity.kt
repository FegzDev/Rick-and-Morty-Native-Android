package com.lawpavilion.rick_and_morty.character.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.lawpavilion.rick_and_morty.core.data.local.converters.StringListConverter

@Entity(tableName = "characters")
@TypeConverters(StringListConverter::class)
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    @Embedded(prefix = "origin_")
    val origin: LocationReference,
    @Embedded(prefix = "location_")
    val location: LocationReference,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    @ColumnInfo(name = "episode_urls")
    val episodeUrls: List<String>,
    val url: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "expires_at")
    val expiresAt: Long?
)