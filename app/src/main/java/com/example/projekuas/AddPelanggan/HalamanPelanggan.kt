package com.example.projekuas.AddPelanggan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projekuas.PenyediaViewModel
import com.example.projekuas.navigation.DestinasiNavigasi
import com.example.projekuas.ui.theme.AddUIState
import com.example.projekuas.ui.theme.DetailPelanggan
import com.example.projekuas.ui.theme.PelangganTopAppBar
import kotlinx.coroutines.launch

object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Entry Siswa"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPelanggan(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    pelangganViewModel: PelangganViewModel = viewModel(factory = PenyediaViewModel.Factory),

    ) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            PelangganTopAppBar(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->

        EntryBody(
            addUIState = pelangganViewModel.addUIState,
            onSiswaValueChange = pelangganViewModel::updateAddUIState,
            onSaveClick = {
                coroutineScope.launch {
                    pelangganViewModel.addPelanggan()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}
@Composable
fun EntryBody(
    addUIState: AddUIState,
    onSiswaValueChange: (DetailPelanggan) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            detailPelanggan = addUIState.detailPelanggan,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    detailPelanggan: DetailPelanggan,
    modifier: Modifier = Modifier,
    onValueChange: (DetailPelanggan) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = detailPelanggan.namaPelanggan,
            onValueChange = { onValueChange(detailPelanggan.copy(namaPelanggan = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailPelanggan.alamat,
            onValueChange = { onValueChange(detailPelanggan.copy(alamat = it)) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailPelanggan.nomorTelepon,
            onValueChange = { onValueChange(detailPelanggan.copy(nomorTelepon = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Telepon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

    }
}
