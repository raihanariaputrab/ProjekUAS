package com.example.projekuas.DetailPelanggan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projekuas.Model.Pelanggan
import com.example.projekuas.PenyediaViewModel
import com.example.projekuas.navigation.DestinasiNavigasi
import com.example.projekuas.ui.theme.DetailUIState
import com.example.projekuas.ui.theme.PelangganTopAppBar
import com.example.projekuas.ui.theme.toPelanggan
import kotlinx.coroutines.launch

object DetailDestination : DestinasiNavigasi {
    override val route = "item_details"
    override val titleRes = "Detail Pelanggan"
    const val pelangganId = "itemId"
    val routeWithArgs = "$route/{$pelangganId}"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigateToEditItem: (String) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPelangganViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            PelangganTopAppBar(
                title = DetailDestination.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditItem(uiState.value.addEvent.IdPelanggan) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "",
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        ItemDetailsBody(
            detailUIState = uiState.value,
            onDelete = {
                // Note: If the user rotates the screen very fast, the operation may get cancelled
                // and the item may not be deleted from the Database. This is because when config
                // change occurs, the Activity will be recreated and the rememberCoroutineScope will
                // be cancelled - since the scope is bound to composition.
                coroutineScope.launch {
                    viewModel.deleteKontak()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),

            )
    }
}
@Composable
private fun ItemDetailsBody(
    detailUIState: DetailUIState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        ItemDetails(
            pelanggan  = detailUIState.addEvent.toPelanggan(), modifier = Modifier.fillMaxWidth()
        )

        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete")
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
@Composable
fun ItemDetails(
    pelanggan: Pelanggan, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ItemDetailsRow(
                labelResID ="Nama",
                itemDetail = pelanggan.namaPelanggan,
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )
            ItemDetailsRow(
                labelResID = "Alamat",
                itemDetail = pelanggan.alamat,
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )
            ItemDetailsRow(
                labelResID ="No. Telpon",
                itemDetail = pelanggan.namaPelanggan,
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )
            ItemDetailsRow(
                labelResID ="Sewa Alat",
                itemDetail = pelanggan.sewaAlat,
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )
            ItemDetailsRow(
                labelResID ="Harga",
                itemDetail = pelanggan.harga,
                modifier = Modifier.padding(
                    horizontal = 12.dp
                )
            )
        }

    }
}
@Composable
private fun ItemDetailsRow(
    labelResID: String, itemDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = labelResID, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemDetail, fontWeight = FontWeight.Bold)
    }
}
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Are you sure") },
        text = { Text("Delete") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "No")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}