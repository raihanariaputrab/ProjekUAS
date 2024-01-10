package com.example.projekuas.ui.theme.HalamanHomeView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekuas.Model.Pelanggan
import com.example.projekuas.database.BarangRepositori
import com.example.projekuas.database.SewaRepository
import com.example.projekuas.ui.theme.AddBarang.BarangViewModel
import com.example.projekuas.ui.theme.Datas
import com.example.projekuas.ui.theme.HomeUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


sealed class PelangganUIState {
    data class Success(val pelanggan: Flow<List<Pelanggan>>) : PelangganUIState()
    object Error : PelangganUIState()
    object Loading : PelangganUIState()
}

class HalamanViewModel(private val sewaRepository: SewaRepository, private val barangRepositori: BarangRepositori): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    val allDataListFlow: Flow<List<Datas>> = barangRepositori.getAllWithPelanggan()
        .map { pairsList ->
            pairsList.mapNotNull { (barang, pelanggan) ->
                if (pelanggan != null){
                    Datas(
                        idPelanggan = pelanggan.idPelanggan,
                        namaPelanggan = pelanggan.namaPelanggan,
                        alamat = pelanggan.alamatPelanggan,
                        nomorTelepon = pelanggan.nomorTelepon,
                        jenisKamera = barang.jenisKamera,
                        jenisLensa = barang.jenisLensa
                    )
                }else{
                    null
                }
            }
        }.flowOn(Dispatchers.IO)

    val homeUIState: StateFlow<HomeUIState> = allDataListFlow
        .filterNotNull()
        .map {
            HomeUIState (listPelanggan = it.toList(), it.size ) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUIState()

        )
}