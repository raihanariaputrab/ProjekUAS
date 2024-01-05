package com.example.projekuas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardOptions.Companion.Default
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pelanggan(
    onConfirmButtonClicked: (String, String, String) -> Unit,
    onCancelButtonClicked: () -> Unit
) {
    var namaPelanggan by remember { mutableStateOf("") }
    var nomorTelepon by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // TextFields
        OutlinedTextField(
            value = namaPelanggan,
            onValueChange = { namaPelanggan = it },
            label = { Text(text = "Nama Pelanggan") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
        OutlinedTextField(
            value = nomorTelepon,
            onValueChange = { nomorTelepon = it },
            label = { Text(text = "Nomor Telepon") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )
        OutlinedTextField(
            value = alamat,
            onValueChange = { alamat = it },
            label = { Text(text = "Alamat") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        )

        // Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onCancelButtonClicked,
                modifier = Modifier
                    .fillMaxWidth(0.45f)
                    .height(50.dp)
            ) {
                Text(text = "Cancel", color = Color.Gray)
            }

            Button(
                onClick = {
                    if (namaPelanggan.isNotEmpty() && nomorTelepon.isNotEmpty() && alamat.isNotEmpty()) {
                        onConfirmButtonClicked(namaPelanggan, nomorTelepon, alamat)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(50.dp)
            ) {
                Text(text = "Next", fontWeight = FontWeight.Bold)
            }
        }
    }
}
