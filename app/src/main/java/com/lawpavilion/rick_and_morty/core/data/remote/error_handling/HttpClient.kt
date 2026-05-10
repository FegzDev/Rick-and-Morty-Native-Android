package com.lawpavilion.rick_and_morty.core.data.remote.error_handling

import com.lawpavilion.rick_and_morty.core.data.remote.models.RemoteData
import com.lawpavilion.rick_and_morty.core.data.remote.responses.ErrorResponse
import com.lawpavilion.rick_and_morty.core.domain.error_handling.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.utils.CacheControl
import io.ktor.http.cacheControl
import io.ktor.http.isSuccess
import kotlinx.coroutines.CancellationException
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException

suspend inline fun <reified D> HttpClient.safeGet(
    url: String,
    block: HttpRequestBuilder.() -> Unit = {}
): RemoteResult<D> {
    return try {
        val response = get(urlString = url, block = block)

        if (response.status.isSuccess()) {
            val maxAge = response.cacheControl()
                .find {
                    it.value.startsWith(
                        prefix = "${CacheControl.MAX_AGE}=",
                        ignoreCase = true
                    )
                }?.value
                ?.substringAfterLast("${CacheControl.MAX_AGE}=")
                ?.toLongOrNull()

            Result.Success(RemoteData(data = response.body(), cacheMaxAge = maxAge))
        } else {
            when (response.status.value) {
                in 300..399 -> Result.Failure(RemoteError.Redirection)
                in 500..599 -> Result.Failure(RemoteError.ServerSide)
                else -> {
                    val bodyResult = runCatching {
                        response.body<ErrorResponse>()
                    }

                    val error = RemoteError.ClientSide(
                        code = response.status.value,
                        error = bodyResult.getOrNull()
                    )

                    Result.Failure(error)
                }
            }
        }
    } catch (exception: Exception) {
        when (exception) {
            is CancellationException -> throw exception
            is IOException -> Result.Failure(RemoteError.Network)
            is SerializationException -> Result.Failure(RemoteError.Serialization)
            else -> Result.Failure(RemoteError.Unknown)
        }
    }
}