package com.example.projekuas

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.projekuas.HalamanHomeView.HalamanViewModel
import com.example.projekuas.AddPelanggan.PelangganViewModel

fun CreationExtras.aplikasiPelanggan(): PelangganApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PelangganApplication)
object PenyediaViewModel {
    val Factory = viewModelFactory {

        initializer {
            PelangganViewModel(aplikasiPelanggan().container.sewaRepository)
        }
        initializer {
            HalamanViewModel(aplikasiPelanggan().container.sewaRepository)
        }
    }
}