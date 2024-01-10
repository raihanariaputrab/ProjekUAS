package com.example.projekuas.ui.theme.AddBarang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.projekuas.database.BarangRepositori
import com.example.projekuas.ui.theme.AddUIState
import com.example.projekuas.ui.theme.AddUIStateBarang
import com.example.projekuas.ui.theme.DetailBarangSewa

import com.example.projekuas.ui.theme.toBarangSewa

class BarangViewModel (private val barangRepositori: BarangRepositori): ViewModel() {
    var addUIStateBarang by mutableStateOf(AddUIStateBarang())
        private set

    fun updateAddUIState(detailBarangSewa: DetailBarangSewa) {
        addUIStateBarang = AddUIStateBarang(detailBarangSewa = detailBarangSewa)
    }

    suspend fun addBarangSewa() {
        barangRepositori.save(addUIStateBarang.detailBarangSewa.toBarangSewa())
    }
}