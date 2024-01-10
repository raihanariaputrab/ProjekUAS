//package com.example.projekuas.ui.theme.EditBarang
//
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.projekuas.navigation.DestinasiNavigasi
//import com.example.projekuas.ui.theme.AddPelanggan.EntryBody
//import com.example.projekuas.ui.theme.EditPelanggan.EditDestination
//import com.example.projekuas.ui.theme.EditPelanggan.EditPelangganViewModel
//import com.example.projekuas.ui.theme.PelangganTopAppBar
//import com.example.projekuas.ui.theme.PenyediaViewModel
//import com.example.projekuas.ui.theme.UiStateBarang
//import kotlinx.coroutines.launch
//
//object EditBarangDestionation : DestinasiNavigasi {
//    override val route = "barang_edit"
//    override val titleRes = "Edit Barang"
//    const val barangId = "barangId"
//    val routeWithArgs = "$route{$barangId}}"
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun EditBarang(
//    navigateBack: () -> Unit,
//    onNavigateUp: () -> Unit,
//    modifier: Modifier = Modifier,
//    viewModel: EditBarangBiewModel = viewModel(factory = PenyediaViewModel.Factory)
//) {
//    val coroutineScope = rememberCoroutineScope()
//    Scaffold(
//        topBar = {
//            PelangganTopAppBar(
//                title = EditDestination.titleRes,
//                canNavigateBack = true,
//                navigateUp = onNavigateUp
//            )
//        },
//        modifier = modifier
//    ) { innerPadding ->
//        EntryBody(
//            addUIStateBarang = viewModel.barangUiState,
//            onBarangValueChange = viewModel::updateUIStateBarang,
//            onSaveClick = {
//                coroutineScope.launch {
//                    viewModel.updateBarang()
//                    navigateBack()
//                }
//            },
//            modifier = Modifier
//                .padding(innerPadding)
//                .verticalScroll(rememberScrollState())
//                .fillMaxWidth()
//        )
//    }
//}