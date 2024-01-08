package com.example.projekuas.Model

data class Pelanggan(
    val IdPelanggan: String = "",
    val namaPelanggan: String = "",
    val nomorTelepon: String = "",
    val alamat: String = "",
    val sewaAlat: String = "",
    val harga: String = ""
){
    constructor(): this("","","","", "")
}
