package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.YearRegistry

@Composable
fun AdventApp() {
    var selectedYear by remember { mutableStateOf<Int?>(null) }
    var selectedDay by remember { mutableStateOf<Int?>(null) }

    MaterialTheme(
        colorScheme = darkColorScheme(),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                DaysList(
                    selectedYear = selectedYear,
                    selectedDay = selectedDay,
                    onDaySelected = { year, day ->
                        selectedYear = year
                        selectedDay = day
                    },
                )

                if (selectedYear != null && selectedDay != null) {
                    DayDetail(year = selectedYear!!, dayNumber = selectedDay!!)
                } else {
                    WelcomeScreen()
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen() {
    val years = YearRegistry.getAllYears()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text(
                text = "🎄",
                style = MaterialTheme.typography.displayLarge,
            )

            Text(
                text = "Advent of Code",
                style = MaterialTheme.typography.headlineLarge,
            )

            // Show progress for each year
            years.forEach { yearInfo ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "${yearInfo.year}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                    )

                    StarProgress(
                        completed = yearInfo.days.size,
                        modifier = Modifier.padding(vertical = 8.dp),
                    )

                    Text(
                        text = "${yearInfo.days.size} / 25 days completed",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Select a day from the sidebar to begin",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}
