package com.example.projekuas.ui.theme

import com.example.projekuas.Model.Pelanggan

data class AddUIState(
    val detailPelanggan: DetailPelanggan = DetailPelanggan(),
)

data class DetailPelanggan(
    val IdPelanggan: String = "",
    val NamaPelanggan: String = "",
    val NomorTelepon: String = "",
    val Alamat: String = ""
)

fun DetailPelanggan.toPelanggan() = Pelanggan(
    idPelanggan = IdPelanggan,
    namaPelanggan = NamaPelanggan,
    alamatPelanggan = Alamat,
    nomorTelepon = NomorTelepon,

)

data class DetailUIState(
    val detailPelanggan: DetailPelanggan = DetailPelanggan(),
)

fun Pelanggan.toDetailPelanggan(): DetailPelanggan =
    DetailPelanggan(
        IdPelanggan = idPelanggan,
        NamaPelanggan = namaPelanggan,
        Alamat = alamatPelanggan,
        NomorTelepon = nomorTelepon,

    )

fun Pelanggan.toUIStatePelanggan(): AddUIState = AddUIState(
    detailPelanggan = this.toDetailPelanggan()
)

data class HomeUIState(
    val listPelanggan: List<Datas> = listOf(),
    val dataLength: Int = 0
)

