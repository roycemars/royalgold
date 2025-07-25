package com.roycemars.royalgold.feature.budget.ui // Adjust package name

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roycemars.royalgold.core.ui.charts.BarChart
import com.roycemars.royalgold.core.ui.charts.PieChart

/**
 * TODO: add Firebase for remote database sync
 */
@Composable
fun BudgetScreen() {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center // This centers the Column if it's smaller than the Box
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PieChart()
            BarChart()
        }
    }
}