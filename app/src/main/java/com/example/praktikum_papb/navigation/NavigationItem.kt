package com.example.praktikum_papb.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val screen: Screen
)
