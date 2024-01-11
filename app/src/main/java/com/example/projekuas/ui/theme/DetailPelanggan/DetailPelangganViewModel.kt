package com.example.projekuas.ui.theme.DetailViewMoedlPelanggan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekuas.database.BarangRepositori
import com.example.projekuas.database.SewaRepository
import com.example.projekuas.ui.theme.Datas
import com.example.projekuas.ui.theme.DatasUi
import com.example.projekuas.ui.theme.DetailPelangga.DetailDestination
import com.example.projekuas.ui.theme.DetailUIDatas
import com.example.projekuas.ui.theme.DetailUIState
import com.example.projekuas.ui.theme.toDatasUi
import com.example.projekuas.ui.theme.toDetailPelanggan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class DetailPelangganViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: SewaRepository,
    private val barangRepositori: BarangRepositori
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val pelangganId: String = checkNotNull(savedStateHandle[DetailDestination.IdPelanggan])

    val dataBasedOnUserId: Flow<Datas> = barangRepositori.getAllBasedOnPelangganId(pelangganId)
        .map { result ->
            requireNotNull(result)
        }
        .map { (barang, pelanggan) ->
            Datas(
                idPelanggan = pelanggan?.idPelanggan?: "",
                namaPelanggan = pelanggan?.namaPelanggan ?: "",
                alamat = pelanggan?.alamatPelanggan ?: "",
                nomorTelepon = pelanggan?.nomorTelepon ?: "",
                jenisKamera = barang.jenisKamera,
                jenisLensa = barang.jenisLensa
            )

        }
        .flowOn(Dispatchers.IO)
    val uiState: StateFlow<DetailUIDatas> =
        dataBasedOnUserId
            .filterNotNull()
            .map {
                DetailUIDatas(datasUi = it.toDatasUi())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = DetailUIDatas()
            )

    suspend fun deletePelanggan() {
        barangRepositori.delete(pelangganId)

    }

}