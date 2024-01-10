//package com.example.projekuas.ui.theme.EditBarang
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.SavedStateHandle
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.projekuas.database.BarangRepositori
//import com.example.projekuas.database.SewaRepository
//import com.example.projekuas.ui.theme.AddUIState
//import com.example.projekuas.ui.theme.AddUIStateBarang
//import com.example.projekuas.ui.theme.DetailBarangSewa
//import com.example.projekuas.ui.theme.DetailPelanggan
//import com.example.projekuas.ui.theme.DetailUIStateBarang
//import com.example.projekuas.ui.theme.EditPelanggan.EditDestination
//import com.example.projekuas.ui.theme.toBarangSewa
//import com.example.projekuas.ui.theme.toDetailBarangSewa
//import com.example.projekuas.ui.theme.toPelanggan
//import com.example.projekuas.ui.theme.toUIStateBarangSewa
//import com.example.projekuas.ui.theme.toUIStatePelanggan
//import kotlinx.coroutines.flow.filterNotNull
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.launch
//
//
//class EditBarangBiewModel (
//    savedStateHandle: SavedStateHandle,
//    private val repository: BarangRepositori
//) : ViewModel() {
//
//    var barangUiState by mutableStateOf(AddUIStateBarang())
//        private set
//
//    private val barangId: String = checkNotNull(savedStateHandle[EditBarangDestionation.barangId])
//
//    init {
//        viewModelScope.launch {
//            barangUiState =
//                repository.getBarangById(barangId)
//                    .filterNotNull()
//                    .first()
//                    .toUIStateBarangSewa()
//        }
//    }
//
//    fun updateUIStateBarang(addEvent: DetailBarangSewa) {
//        barangUiState = barangUiState.copy(detailBarangSewa = addEvent)
//    }
//
//    suspend fun updateBarang() {
//        repository.update(barangUiState.detailBarangSewa.toBarangSewa())
//
//    }
//}