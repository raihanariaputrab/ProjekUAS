package com.example.projekuas.ui.theme.AddBarang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.projekuas.database.BarangRepositori
import com.example.projekuas.database.SewaRepository
import com.example.projekuas.ui.theme.AddUIState
import com.example.projekuas.ui.theme.AddUIStateBarang
import com.example.projekuas.ui.theme.DetailBarang.DetailBarangDestination
import com.example.projekuas.ui.theme.DetailBarangSewa
import com.example.projekuas.ui.theme.DetailPelanggan

import com.example.projekuas.ui.theme.toBarangSewa
import com.example.projekuas.ui.theme.toPelanggan

class BarangViewModel (private val barangRepositori: BarangRepositori, private val sewaRepository: SewaRepository): ViewModel() {

    var addUIState by mutableStateOf(AddUIState())
        private set
    var id : String = ""
    var addUIStateBarang by mutableStateOf(AddUIStateBarang())
        private set

    fun updateAddUIState(detailBarangSewa: DetailBarangSewa) {
        addUIStateBarang = AddUIStateBarang(detailBarangSewa = detailBarangSewa)
    }

    fun updateAddUIState(detailPelanggan: DetailPelanggan) {
        addUIState = AddUIState(detailPelanggan = detailPelanggan)
    }



    suspend fun addPelanggan() {
        id = sewaRepository.save(addUIState.detailPelanggan.toPelanggan())
    }
    suspend fun addBarangSewa() {
        val barang = addUIStateBarang.detailBarangSewa.toBarangSewa().copy(
            idPelanggan = id
        )
        barangRepositori.save(barang)
    }


}