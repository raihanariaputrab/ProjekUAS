package com.example.projekuas.ui.theme

import com.example.projekuas.Model.BarangSewa

data class AddUIStateBarang(
    val detailBarangSewa: DetailBarangSewa = DetailBarangSewa()
)
data class DetailBarangSewa(
    val IdSewa: String = "",
    val JenisKamera: String = "",
    val JenisLensa: String = ""
)

fun DetailBarangSewa.toBarangSewa() = BarangSewa (
    idSewa = IdSewa,
    jenisKamera = JenisKamera,
    jenisLensa = JenisLensa
)
data class DetailUIStateBarang(
    val detailBarangSewa: DetailBarangSewa = DetailBarangSewa()
)

fun BarangSewa.toDetailBarangSewa(): DetailBarangSewa =
    DetailBarangSewa(
        IdSewa = idSewa,
        JenisKamera = jenisKamera,
        JenisLensa = jenisLensa,
    )
fun BarangSewa.toUIStateBarangSewa(): AddUIStateBarang = AddUIStateBarang(
    detailBarangSewa = this.toDetailBarangSewa()
)