package com.example.projekuas.DetailPelanggan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekuas.database.SewaRepository
import com.example.projekuas.ui.theme.DetailUIState
import com.example.projekuas.ui.theme.toDetailPelanggan
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailPelangganViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: SewaRepository
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val pelangganId: String = checkNotNull(savedStateHandle[DetailDestination.pelangganId])

    val uiState: StateFlow<DetailUIState> =
        repository.getPelangganById(pelangganId)
            .filterNotNull()
            .map {
                DetailUIState(addEvent = it.toDetailPelanggan())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DetailUIState()
            )

    suspend fun deleteKontak() {
        repository.delete(pelangganId)

    }


}