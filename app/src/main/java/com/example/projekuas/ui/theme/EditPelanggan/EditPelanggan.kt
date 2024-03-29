package com.example.projekuas.ui.theme.EditPelanggan

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projekuas.ui.theme.AddPelanggan.EntryBody
import com.example.projekuas.ui.theme.PenyediaViewModel
import com.example.projekuas.navigation.DestinasiNavigasi
import com.example.projekuas.ui.theme.PelangganTopAppBar
import kotlinx.coroutines.launch

object EditDestination : DestinasiNavigasi {
    override val route = "item_edit"
    override val titleRes ="Edit Kontak"
    const val pelangganId = "itemId"
    val routeWithArgs = "$route/{$pelangganId}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditPelangganViewModel = viewModel(factory = PenyediaViewModel.Factory),
    pilihanK: List<String>,
    pilihanL: List<String>
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            PelangganTopAppBar(
                title = EditDestination.titleRes,
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryBody(
            addUIState = viewModel.sewaUiState,
            onPelangganValueChange = viewModel::updateUIState,



            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePelanggan()
                    viewModel.updateB()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            addUIStateBarang = viewModel.sewaUiStateB,
            onBarangValueChange = viewModel::updateUIStateB,
            jenisLensa = pilihanL,
            jenisKamera = pilihanK
        )
    }
}