package com.lawpavilion.rick_and_morty.character.data.repositories

import com.lawpavilion.rick_and_morty.character.data.local.data_sources.LocalCharacterDataSource
import com.lawpavilion.rick_and_morty.character.data.local.mappers.toCharacter
import com.lawpavilion.rick_and_morty.character.data.local.mappers.toEntity
import com.lawpavilion.rick_and_morty.character.data.remote.data_sources.RemoteCharacterDataSource
import com.lawpavilion.rick_and_morty.character.data.remote.mappers.toCharacter
import com.lawpavilion.rick_and_morty.core.data.remote.mappers.toDomainResult
import com.lawpavilion.rick_and_morty.character.data.remote.mappers.toPage
import com.lawpavilion.rick_and_morty.character.domain.models.Character
import com.lawpavilion.rick_and_morty.character.domain.models.CharacterPage
import com.lawpavilion.rick_and_morty.character.domain.models.CharacterPageInfo
import com.lawpavilion.rick_and_morty.character.domain.repositories.CharacterRepository
import com.lawpavilion.rick_and_morty.core.data.local.utils.LocalDataDefaults
import com.lawpavilion.rick_and_morty.core.domain.error_handling.DomainResult
import com.lawpavilion.rick_and_morty.core.domain.error_handling.Result
import com.lawpavilion.rick_and_morty.core.domain.error_handling.onSuccess
import jakarta.inject.Inject
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds

class CharacterRepositoryImpl @Inject constructor(
    private val localDataSource: LocalCharacterDataSource,
    private val remoteDataSource: RemoteCharacterDataSource
) : CharacterRepository {
    override suspend fun getPage(
        index: Int,
        name: String?,
        status: String?,
        species: String?
    ): DomainResult<CharacterPage> {
        val hasParameters = !name.isNullOrBlank() ||
                !status.isNullOrBlank() ||
                !species.isNullOrBlank()

        if (!hasParameters) {
            val localPageSize = LocalDataDefaults.CHARACTER_PAGE_SIZE

            val offset = (index - 1).coerceAtLeast(0) * localPageSize

            val localCharacters = localDataSource.getCharacters(
                offset = offset,
                count = localPageSize + 1
            )

            val isAnyCharacterExpired = localCharacters.any { entity ->
                entity.expiresAt == null ||
                        entity.expiresAt < Clock.System.now().toEpochMilliseconds()
            }

            if (localCharacters.isNotEmpty() && !isAnyCharacterExpired) {
                val info = CharacterPageInfo(
                    previous = if (index == 1) null else index - 1,
                    next = if (localCharacters.size <= localPageSize) null else index.plus(1)
                )

                val characters = localCharacters.take(localPageSize).map { it.toCharacter() }

                return Result.Success(CharacterPage(info = info, characters = characters))
            }
        }

        val remoteResult = remoteDataSource.getPage(
            index = index,
            name = name,
            status = status,
            species = species
        )

        return remoteResult.onSuccess { data ->
            if (!hasParameters) {
                val page = data.data.toPage()

                val expiresAt = data.cacheMaxAge?.let { age ->
                    Clock.System.now()
                        .plus(age.seconds)
                        .toEpochMilliseconds()
                }

                val newLocalCharacters = page.characters.map { it.toEntity(expiresAt) }

                localDataSource.upsertCharacters(newLocalCharacters)
            }
        }.toDomainResult { it.data.toPage() }
    }

    override suspend fun getCharacter(id: Int): DomainResult<Character> {
        val localCharacter = localDataSource.getCharacter(id)

        if (
            localCharacter?.expiresAt != null &&
            localCharacter.expiresAt > Clock.System.now().toEpochMilliseconds()
        ) {
            return Result.Success(localCharacter.toCharacter())
        }

        val remoteResult = remoteDataSource.getCharacter(id)

        return remoteResult.onSuccess { data ->
            val character = data.data.toCharacter()

            val expiresAt = data.cacheMaxAge?.let { age ->
                Clock.System.now()
                    .plus(age.seconds)
                    .toEpochMilliseconds()
            }

            localDataSource.upsertCharacter(character.toEntity(expiresAt))
        }.toDomainResult { it.data.toCharacter() }
    }
}