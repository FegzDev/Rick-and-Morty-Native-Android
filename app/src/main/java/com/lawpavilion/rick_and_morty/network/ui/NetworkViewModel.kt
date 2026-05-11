package com.lawpavilion.rick_and_morty.network.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lawpavilion.rick_and_morty.network.domain.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(networkMonitor: NetworkMonitor) : ViewModel() {
    val isOnline: StateFlow<Boolean> = networkMonitor.isOnline.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = true
    )
}