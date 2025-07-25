package com.roycemars.royalgold.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AreaChart
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.DonutSmall
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AreaChart
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.DonutSmall
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    data object Expenses : Screen(
        route = "expenses",
        title = "Expenses",
        selectedIcon = Icons.Filled.Camera,
        unselectedIcon = Icons.Outlined.Camera
    )
    data object Budget : Screen(
        route = "budget",
        title = "Budget",
        selectedIcon = Icons.Filled.DonutSmall,
        unselectedIcon = Icons.Outlined.DonutSmall
    )
    data object Portfolio : Screen(
        route = "portfolio",
        title = "Portfolio",
        selectedIcon = Icons.Filled.MonetizationOn,
        unselectedIcon = Icons.Outlined.MonetizationOn
    )
    data object Market : Screen(
        route = "market",
        title = "Market",
        selectedIcon = Icons.Filled.AreaChart,
        unselectedIcon = Icons.Outlined.AreaChart
    )
    data object Settings : Screen(
        route = "settings",
        title = "Settings",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings
    )
}

val bottomNavItems = listOf(
    Screen.Portfolio,
    Screen.Budget,
    Screen.Expenses,
    Screen.Market,
    Screen.Settings
)