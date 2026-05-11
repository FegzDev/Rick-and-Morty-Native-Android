package com.lawpavilion.rick_and_morty.network.domain

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}