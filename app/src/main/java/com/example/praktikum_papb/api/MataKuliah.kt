package com.example.praktikum_papb.api

data class Pertemuan(
    val hari: String = "",
    val jam_mulai: String = "",
    val jam_selesai: String = "",
    val ruangan: String = "",
    val praktikum: Boolean = false
)


data class MataKuliah(
    val nama: String = "",
    val pertemuan: List<Pertemuan> = emptyList()
)
