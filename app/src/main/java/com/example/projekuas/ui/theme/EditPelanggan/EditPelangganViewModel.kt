package com.example.projekuas.ui.theme.EditPelanggan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekuas.database.BarangRepositori
import com.example.projekuas.database.SewaRepository
import com.example.projekuas.ui.theme.AddUIState
import com.example.projekuas.ui.theme.AddUIStateBarang
import com.example.projekuas.ui.theme.DetailBarangSewa
import com.example.projekuas.ui.theme.DetailPelanggan
import com.example.projekuas.ui.theme.toBarangSewa
import com.example.projekuas.ui.theme.toPelanggan
import com.example.projekuas.ui.theme.toUIStateBarangSewa
import com.example.projekuas.ui.theme.toUIStatePelanggan
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EditPelangganViewModel (
    savedStateHandle: SavedStateHandle,
    private val repository: SewaRepository,
    private val repositori: BarangRepositori
) : ViewModel() {

    var sewaUiState by mutableStateOf(AddUIState())
        private set

    var sewaUiStateB by mutableStateOf(AddUIStateBarang())
        private set

    private val pelangganId: String = checkNotNull(savedStateHandle[EditDestination.pelangganId])

    init {
        viewModelScope.launch {
            sewaUiState =
                repository.getPelangganById(pelangganId)
                    .filterNotNull()
                    .first()
                    .toUIStatePelanggan()
        }
    }

    init {
        viewModelScope.launch {
            sewaUiStateB = repositori.getBarangSewaByPelangganId(pelangganId).filterNotNull().first().toUIStateBarangSewa()
        }
    }

    fun updateUIState(addEvent: DetailPelanggan) {
        sewaUiState = sewaUiState.copy(detailPelanggan = addEvent)
    }
    fun updateUIStateB(addBarang: DetailBarangSewa){
        sewaUiStateB = sewaUiStateB.copy(detailBarangSewa = addBarang)
    }

    suspend fun updatePelanggan() {
        repository.update(sewaUiState.detailPelanggan.toPelanggan())

    }

    suspend fun updateB(){
        repositori.update(sewaUiStateB.detailBarangSewa.toBarangSewa())
    }

}