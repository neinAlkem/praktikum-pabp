package com.example.praktikum_papb.navigation

import okhttp3.Route

sealed class Screen(val route: String) {
    object Matkul : Screen("Matkul")
    object Tugas : Screen("Tugas")
    object Profile : Screen("Profile")
}