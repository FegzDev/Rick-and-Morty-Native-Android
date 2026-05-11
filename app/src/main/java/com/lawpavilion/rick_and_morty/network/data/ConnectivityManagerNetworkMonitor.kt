package com.lawpavilion.rick_and_morty.network.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.lawpavilion.rick_and_morty.network.domain.NetworkMonitor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ConnectivityManagerNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context
) : NetworkMonitor {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override val isOnline: Flow<Boolean> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                trySend(false)
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                val hasInternet = networkCapabilities
                    .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

                val isValidated = networkCapabilities
                    .hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

                trySend(hasInternet && isValidated)
            }
        }

        val activeNetwork = connectivityManager.activeNetwork

        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        val hasInternet = capabilities?.run {
            hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } ?: false

        trySend(hasInternet)

        connectivityManager.registerDefaultNetworkCallback(callback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged().conflate()
}