package com.lawpavilion.rick_and_morty.character.data.remote.data_sources

import com.lawpavilion.rick_and_morty.core.domain.error_handling.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Test

class KtorCharacterDataSourceTest {

    @Test
    fun `getPage success returns CharacterPageResponse`() = runTest {
        val jsonResponse = """
            {
              "info": {
                "count": 826,
                "pages": 42,
                "next": "https://rickandmortyapi.com/api/character/?page=2",
                "prev": null
              },
              "results": [
                {
                  "id": 1,
                  "name": "Rick Sanchez",
                  "status": "Alive",
                  "species": "Human",
                  "type": "",
                  "gender": "Male",
                  "origin": {
                    "name": "Earth",
                    "url": "url"
                  },
                  "location": {
                    "name": "Earth",
                    "url": "url"
                  },
                  "image": "image",
                  "episode": ["episodeUrl"],
                  "url": "url",
                  "created": "2017-11-04T18:48:46.250Z"
                }
              ]
            }
        """.trimIndent()

        val mockEngine = MockEngine {
            respond(
                content = jsonResponse,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val dataSource = KtorCharacterDataSource(createClient(mockEngine))

        val result = dataSource.getPage(
            index = 1,
            name = null,
            status = null,
            species = null
        )

        assertTrue(result is Result.Success)

        val success = result as Result.Success

        assertTrue(success.data.data.results.size == 1)
        assertTrue(success.data.data.results[0].name == "Rick Sanchez")
    }

    @Test
    fun `getCharacter success returns CharacterResponse`() = runTest {
        val jsonResponse = """
            {
              "id": 1,
              "name": "Rick Sanchez",
              "status": "Alive",
              "species": "Human",
              "type": "",
              "gender": "Male",
              "origin": {
                "name": "Earth",
                "url": "url"
              },
              "location": {
                "name": "Earth",
                "url": "url"
              },
              "image": "image",
              "episode": ["episodeUrl"],
              "url": "url",
              "created": "2017-11-04T18:48:46.250Z"
            }
        """.trimIndent()

        val mockEngine = MockEngine {
            respond(
                content = jsonResponse,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val dataSource = KtorCharacterDataSource(createClient(mockEngine))

        val result = dataSource.getCharacter(1)

        assertTrue(result is Result.Success)

        val success = result as Result.Success

        assertTrue(success.data.data.name == "Rick Sanchez")
    }

    private fun createClient(engine: MockEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }
}
