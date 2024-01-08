package com.example.projekuas.ui.theme

import com.example.projekuas.Model.Pelanggan

data class AddUIState(
    val detailPelanggan: DetailPelanggan = DetailPelanggan(),
)

data class DetailPelanggan(
    val IdPelanggan: String = "",
    val namaPelanggan: String = "",
    val nomorTelepon: String = "",
    val alamat: String = "",
    val sewaAlat: String = "",
    val harga: String = ""
)

fun DetailPelanggan.toPelanggan() = Pelanggan(
    IdPelanggan = IdPelanggan,
    namaPelanggan = namaPelanggan,
    alamat = alamat,
    nomorTelepon = nomorTelepon,
    sewaAlat = sewaAlat,
    harga = harga
)

data class DetailUIState(
    val addEvent: DetailPelanggan = DetailPelanggan(),
)

fun Pelanggan.toDetailPelanggan(): DetailPelanggan =
    DetailPelanggan(
        IdPelanggan = IdPelanggan,
        namaPelanggan = namaPelanggan,
        alamat = alamat,
        nomorTelepon = nomorTelepon,
        sewaAlat = sewaAlat,
        harga = harga
    )

fun Pelanggan.toUIStatePelanggan(): AddUIState = AddUIState(
    detailPelanggan = this.toDetailPelanggan()
)

data class HomeUIState(
    val listPelanggan: List<Pelanggan> = listOf(),
    val dataLength: Int = 0
)