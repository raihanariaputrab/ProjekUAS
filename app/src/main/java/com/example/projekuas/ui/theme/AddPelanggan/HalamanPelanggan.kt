package com.example.projekuas.ui.theme.AddPelanggan

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
import com.example.projekuas.ui.theme.PenyediaViewModel
import com.example.projekuas.navigation.DestinasiNavigasi
import com.example.projekuas.ui.theme.AddBarang.BarangViewModel
import com.example.projekuas.ui.theme.AddUIState
import com.example.projekuas.ui.theme.AddUIStateBarang
import com.example.projekuas.ui.theme.DetailBarangSewa
import com.example.projekuas.ui.theme.DetailPelanggan
import com.example.projekuas.ui.theme.PelangganTopAppBar
import kotlinx.coroutines.launch

object DestinasiEntry : DestinasiNavigasi {
    override val route = "item_entry"
    override val titleRes = "Isi Data Untuk Booking"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanPelanggan(
    navigateBack: () -> Unit,
    navigateToBarang: () -> Unit,
    modifier: Modifier = Modifier,
    pelangganViewModel: BarangViewModel = viewModel(factory = PenyediaViewModel.Factory),

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
            onPelangganValueChange = pelangganViewModel::updateAddUIState,
            onSaveClick = {
                coroutineScope.launch {
                    pelangganViewModel.addPelanggan()
                    pelangganViewModel.addBarangSewa()
                    navigateToBarang()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            addUIStateBarang = pelangganViewModel.addUIStateBarang,
            onBarangValueChange = pelangganViewModel::updateAddUIState
        )
    }
}

@Composable
fun EntryBody(
    addUIState: AddUIState,
    addUIStateBarang: AddUIStateBarang,
    onPelangganValueChange: (DetailPelanggan) -> Unit,
    onBarangValueChange: (DetailBarangSewa) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            detailPelanggan = addUIState.detailPelanggan,
            onValueChange = onPelangganValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        FormInputBarang(
            detailBarangSewa = addUIStateBarang.detailBarangSewa,
            onValueChangeB = onBarangValueChange,
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
            value = detailPelanggan.NamaPelanggan,
            onValueChange = { onValueChange(detailPelanggan.copy(NamaPelanggan = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailPelanggan.Alamat,
            onValueChange = { onValueChange(detailPelanggan.copy(Alamat = it)) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailPelanggan.NomorTelepon,
            onValueChange = { onValueChange(detailPelanggan.copy(NomorTelepon = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(text = "Telepon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputBarang(
    detailBarangSewa: DetailBarangSewa,
    modifier: Modifier = Modifier,
    onValueChangeB : (DetailBarangSewa) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = detailBarangSewa.JenisKamera,
            onValueChange = { onValueChangeB(detailBarangSewa.copy(JenisKamera = it)) },
            label = { Text("Jenis Kamera") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = detailBarangSewa.JenisLensa,
            onValueChange = { onValueChangeB(detailBarangSewa.copy(JenisLensa = it)) },
            label = { Text("Jenis Lensa") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}
