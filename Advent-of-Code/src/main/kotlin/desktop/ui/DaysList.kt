package desktop.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import days.DayRegistry

@Composable
fun DaysList(
    selectedDay: Int?,
    onDaySelected: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight(),
        tonalElevation = 2.dp
    ) {
        Column {
            Surface(
                tonalElevation = 4.dp
            ) {
                Text(
                    text = "Days",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(DayRegistry.getAllDayNumbers()) { dayNumber ->
                    DayListItem(
                        dayNumber = dayNumber,
                        isImplemented = DayRegistry.isDayImplemented(dayNumber),
                        isSelected = dayNumber == selectedDay,
                        onClick = { onDaySelected(dayNumber) }
                    )
                }
            }
        }
    }
}

@Composable
fun DayListItem(
    dayNumber: Int,
    isImplemented: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = isImplemented,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Day $dayNumber")
            if (isImplemented) {
                Text("✓", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}