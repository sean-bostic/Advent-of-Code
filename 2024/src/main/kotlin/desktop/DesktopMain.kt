package desktop

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.unit.dp
import desktop.ui.AdventApp

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "🎄 Advent of Code 2024",
        state = rememberWindowState(width = 1000.dp, height = 700.dp)
    ) {
        AdventApp()
    }
}