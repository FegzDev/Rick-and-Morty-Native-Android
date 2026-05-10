package com.lawpavilion.rick_and_morty.core.data.remote.error_handling

import com.lawpavilion.rick_and_morty.core.domain.error_handling.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

@Serializable
private data class TestResponse(val name: String)

class HttpClientTest {
    @Test
    fun safeGetReturnsSuccessResultOnSuccess() = runTest {
        val response = """
            {
              "name": "Test"
            }
        """.trimIndent()

        val engine = MockEngine {
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = createClient(engine)

        val result = client.safeGet<TestResponse>("test_url")

        assertTrue(result is Result.Success)

        val successResult = result as Result.Success

        assertEquals("Test", successResult.data.data.name)
    }

    @Test
    fun safeGetReturnsRedirectionErrorResultWhenStatusCodeIn300s() = runTest {
        val engine = MockEngine {
            respond(
                content = "",
                status = HttpStatusCode.NotModified,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = createClient(engine)

        val result = client.safeGet<TestResponse>("test_url")

        assertTrue(result is Result.Failure)

        val failureResult = result as Result.Failure

        assertTrue(failureResult.error is RemoteError.Redirection)
    }

    @Test
    fun safeGetReturnsServerSideErrorResultWhenStatusCodeIn500s() = runTest {
        val engine = MockEngine {
            respond(
                content = "",
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = createClient(engine)

        val result = client.safeGet<TestResponse>("test_url")

        assertTrue(result is Result.Failure)

        val failureResult = result as Result.Failure

        assertTrue(failureResult.error is RemoteError.ServerSide)
    }

    @Test
    fun safeGetReturnsServerSideErrorResultWhenStatusCodeIn400s() = runTest {
        val response = """
            {
              "error": "Test"
            }
        """.trimIndent()

        val engine = MockEngine {
            respond(
                content = response,
                status = HttpStatusCode.BadRequest,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = createClient(engine)

        val result = client.safeGet<TestResponse>("test_url")

        assertTrue(result is Result.Failure)

        val failureResult = result as Result.Failure

        assertTrue(failureResult.error is RemoteError.ClientSide)

        val clientSideResult = failureResult.error as RemoteError.ClientSide

        assertEquals("Test", clientSideResult.error?.error)
    }

    @Test
    fun safeGetRethrowsCancellationException() = runTest {
        val exception = CancellationException("Coroutine was cancelled")

        val engine = MockEngine {
            throw exception
        }

        val client = createClient(engine)

        val thrownException = assertThrows(CancellationException::class.java) {
            runBlocking {
                client.safeGet<TestResponse>("test_url")
            }
        }

        assertEquals(exception.message, thrownException.message)
    }

    @Test
    fun safeGetReturnsNetworkErrorResultWhenIOExceptionOccurs() = runTest {
        val exception = IOException("Coroutine was cancelled")

        val engine = MockEngine {
            throw exception
        }

        val client = createClient(engine)

        val result = client.safeGet<TestResponse>("test_url")

        assertTrue(result is Result.Failure)

        val failureResult = result as Result.Failure

        assertTrue(failureResult.error is RemoteError.Network)
    }

    @Test
    fun safeGetReturnsSerializationErrorResultWhenSerializationExceptionOccurs() = runTest {
        val engine = MockEngine {
            respond(
                content = "",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = createClient(engine)

        val result = client.safeGet<TestResponse>("test_url")

        assertTrue(result is Result.Failure)

        val failureResult = result as Result.Failure

        assertTrue(failureResult.error is RemoteError.Serialization)
    }

    @Test
    fun safeGetReturnsUnknowErrorResultWhenUnexpectedExceptionOccurs() = runTest {
        val exception = Exception("Coroutine was cancelled")

        val engine = MockEngine {
            throw exception
        }

        val client = createClient(engine)

        val result = client.safeGet<TestResponse>("test_url")

        assertTrue(result is Result.Failure)

        val failureResult = result as Result.Failure

        assertTrue(failureResult.error is RemoteError.Unknown)
    }

    private fun createClient(engine: MockEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}
