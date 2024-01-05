package com.example.projekuas

import androidx.lifecycle.ViewModel
import com.example.projekuas.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat

private const val HARGA_PER_CUP = 3000

class OrderViewModel : ViewModel() {
    private val _stateUI = MutableStateFlow(OrderUiState())
    val stateUI: StateFlow<OrderUiState> = _stateUI.asStateFlow()

    fun setPelanggan(nama: String, nomor: String, alamat:String){
        _stateUI.update { stateSaatIni ->
            stateSaatIni.copy(
                namaPelanggan = nama,
                nomorTelepon = nomor,
                alamat = alamat
            )
        }
    }

    fun setJumlah(jmlEsJumbo:Int){
        _stateUI.update { stateSaatIni ->
            stateSaatIni.copy(jumlah = jmlEsJumbo,
                harga = hitungHarga(jumlah = jmlEsJumbo))
        }
    }

    fun setKamera(pilihanKamera: String){
        _stateUI.update { stateSaatIni ->
            stateSaatIni.copy(alat = pilihanKamera)
        }
    }
    fun setLensa(pilihanLensa: String){
        _stateUI.update { stateSaatIni ->
            stateSaatIni.copy(alat = pilihanLensa)
        }
    }

    fun resetOrder(){
        _stateUI.value = OrderUiState()
    }

    private fun hitungHarga(
        jumlah: Int = _stateUI.value.jumlah,
    ): String{
        val kalkulasiHarga = jumlah * HARGA_PER_CUP

        return NumberFormat.getNumberInstance().format(kalkulasiHarga)
    }
}