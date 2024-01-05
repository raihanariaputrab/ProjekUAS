package com.example.projekuas

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.projekuas.data.SumberDataKamera.kamera
import com.example.projekuas.data.SumberDataLensa.lensa

enum class PengelolaHalaman {
    Home,
    Lensa,
    Kamera,
    Menu,
    Summary,
    Pelanggan,

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EsJumboAppBar(
    bisaNavigasiBack: Boolean,
    navigasiUp: () -> Unit,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = {Text(stringResource(id = R.string.app_name))},
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (bisaNavigasiBack){
                IconButton(onClick = navigasiUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button))
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EsJumboApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        topBar = {
            EsJumboAppBar(
                bisaNavigasiBack = false,
                navigasiUp = { }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.stateUI.collectAsState()
        NavHost(
            navController = navController,
            startDestination = PengelolaHalaman.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = PengelolaHalaman.Home.name) {
                HalamanUtama(
                    onNextButton1Clicked = {
                        navController.navigate(PengelolaHalaman.Pelanggan.name)
                    }
                )
            }
            composable(route = PengelolaHalaman.Pelanggan.name) {
                Pelanggan(
                    onConfirmButtonClicked = { nama, nomor, alamat ->
                        viewModel.setPelanggan(nama, nomor, alamat)
                        navController.navigate(PengelolaHalaman.Menu.name)
                    },
                    onCancelButtonClicked = {
                        navController.navigate(PengelolaHalaman.Home.name)
                    }
                )
            }
            composable(route = PengelolaHalaman.Menu.name) {
                HalamanMenu(
                    onNextButtonClicked = {
                        navController.navigate(PengelolaHalaman.Lensa.name)
                    },
                    onNextButton2Clicked = {
                        navController.navigate(PengelolaHalaman.Kamera.name)
                    }
                )
            }


            composable(route = PengelolaHalaman.Lensa.name) {
                val context = LocalContext.current
                HalamanLensa(
                    pilihanLensa = lensa.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setLensa(it) },
                    onConfirmButtonClicked = { viewModel.setJumlah(it) },
                    onNextButtonClicked = { navController.navigate(PengelolaHalaman.Summary.name) },
                    onCancelButtonClicked = { navController.navigate(PengelolaHalaman.Menu.name) }
                )
            }
            composable(route = PengelolaHalaman.Kamera.name) {
                val context = LocalContext.current
                HalamanKamera(
                    pilihanKamera = kamera.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setKamera(it) },
                    onConfirmButtonClicked = { viewModel.setJumlah(it) },
                    onNextButtonClicked = { navController.navigate(PengelolaHalaman.Summary.name) },
                    onCancelButtonClicked = { navController.navigate(PengelolaHalaman.Menu.name) }
                )
            }
            composable(route = PengelolaHalaman.Summary.name) {
                HalamanDua(
                    orderUIState = uiState,
                    onCancelButtonClicked = { cancelOrderAndNavigateToRasa(navController) },
                )
            }
        }
    }
}


private fun cancelOrderAndNavigateToHome(
    viewModel: OrderViewModel,
    navController: NavHostController
){
    viewModel.resetOrder()
    navController.popBackStack(PengelolaHalaman.Home.name, inclusive = false)
}
private fun cancelOrderAndNavigateToRasa(
    navController: NavHostController
){
    navController.popBackStack(PengelolaHalaman.Lensa.name, inclusive = false)
}