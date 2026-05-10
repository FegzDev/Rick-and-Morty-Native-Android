package com.lawpavilion.rick_and_morty.network.di

import com.lawpavilion.rick_and_morty.core.data.remote.utils.RemoteDataDefaults
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import jakarta.inject.Singleton
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            defaultRequest {
                url(RemoteDataDefaults.BASE_URL)
            }

            install(Logging) {
                logger = Logger.SIMPLE
                sanitizeHeader { header ->
                    header == HttpHeaders.Authorization
                }
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        coerceInputValues = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}