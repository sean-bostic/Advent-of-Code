package desktop.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdventApp() {
    var selectedDay by remember { mutableStateOf<Int?>(null) }

    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                DaysList(
                    selectedDay = selectedDay,
                    onDaySelected = { selectedDay = it }
                )

                selectedDay?.let { day ->
                    DayDetail(dayNumber = day)
                } ?: run {
                    WelcomeScreen()
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    val completedStars = days.DayRegistry.getCompletedStars()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "🎄",
                style = MaterialTheme.typography.displayLarge
            )

            Text(
                text = "Advent of Code 2024",
                style = MaterialTheme.typography.headlineLarge
            )

            StarProgress(
                completed = completedStars,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Text(
                text = "$completedStars / 25 days completed",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Select a day from the sidebar to begin",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}