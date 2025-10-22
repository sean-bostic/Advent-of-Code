package ui

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import aoc_2024.Year2024
import core.YearRegistry

fun main() =
    application {
        YearRegistry.register(2024, Year2024.getAllDays())

        Window(
            onCloseRequest = ::exitApplication,
            title = "🎄 Advent of Code",
            state = rememberWindowState(width = 1000.dp, height = 700.dp),
        ) {
            AdventApp()
        }
    }
