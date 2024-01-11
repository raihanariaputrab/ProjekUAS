package com.example.projekuas.ui.theme

import com.example.projekuas.Model.BarangSewa

data class AddUIStateBarang(
    val detailBarangSewa: DetailBarangSewa = DetailBarangSewa()
)
data class DetailBarangSewa(
    val IdPelanggan: String = "",
    val IdSewa: String = "",
    val JenisKamera: String = "",
    val JenisLensa: String = ""
)

fun DetailBarangSewa.toBarangSewa() = BarangSewa (
    idPelanggan = IdPelanggan,
    idSewa = IdSewa,
    jenisKamera = JenisKamera,
    jenisLensa = JenisLensa
)
data class DetailUIStateBarang(
    val detailBarangSewa: DetailBarangSewa = DetailBarangSewa()
)

fun BarangSewa.toDetailBarangSewa(): DetailBarangSewa =
    DetailBarangSewa(
        IdPelanggan = idPelanggan,
        IdSewa = idSewa,
        JenisKamera = jenisKamera,
        JenisLensa = jenisLensa,
    )
fun BarangSewa.toUIStateBarangSewa(): AddUIStateBarang = AddUIStateBarang(
    detailBarangSewa = this.toDetailBarangSewa()
)

data class DatasUi(
    val IdPelanggan: String = "",
    val NamaPelanggan: String = "",
    val NomorTelepon: String = "",
    val Alamat: String = "",
    val JenisKamera: String = "",
    val JenisLensa: String = ""
)


data class DetailUIDatas(
    val datasUi: DatasUi = DatasUi()
)
fun Datas.toDatasUi(): DatasUi =
    DatasUi(
        IdPelanggan = idPelanggan,
        NamaPelanggan = namaPelanggan,
        NomorTelepon = nomorTelepon,
        Alamat = alamat,
        JenisKamera = jenisKamera,
        JenisLensa = jenisLensa
    )

fun DatasUi.toDatas(): Datas =
    Datas(
        idPelanggan = IdPelanggan,
        namaPelanggan = NamaPelanggan,
        nomorTelepon = NomorTelepon,
        alamat = Alamat,
        jenisKamera = JenisKamera,
        jenisLensa = JenisLensa
    )


//untuk menampung data gabungan
data class Datas(
    val idPelanggan: String ,
    val namaPelanggan: String,
    val nomorTelepon: String ,
    val alamat: String ,
    val jenisKamera: String,
    val jenisLensa: String ,
) {
    constructor() : this("", "", "", "", "", "")
}