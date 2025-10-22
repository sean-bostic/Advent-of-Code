package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.YearRegistry

@Composable
fun DaysList(
    selectedYear: Int?,
    selectedDay: Int?,
    onDaySelected: (year: Int, day: Int) -> Unit,
) {
    var expandedYears by remember { mutableStateOf(setOf<Int>()) }

    LaunchedEffect(Unit) {
        val mostRecentYear = YearRegistry.getAllYears().firstOrNull()?.year
        if (mostRecentYear != null) {
            expandedYears = setOf(mostRecentYear)
        }
    }

    Surface(
        modifier =
            Modifier
                .width(280.dp)
                .fillMaxHeight(),
        tonalElevation = 2.dp,
    ) {
        Column {
            Surface(
                tonalElevation = 4.dp,
            ) {
                Text(
                    text = "Advent of Code",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp),
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                YearRegistry.getAllYears().forEach { yearInfo ->
                    item(key = "year_${yearInfo.year}") {
                        YearHeader(
                            year = yearInfo.year,
                            dayCount = yearInfo.days.size,
                            isExpanded = yearInfo.year in expandedYears,
                            onToggle = {
                                expandedYears =
                                    if (yearInfo.year in expandedYears) {
                                        expandedYears - yearInfo.year
                                    } else {
                                        expandedYears + yearInfo.year
                                    }
                            },
                        )
                    }

                    if (yearInfo.year in expandedYears) {
                        items(
                            items = yearInfo.days,
                            key = { day -> "day_${yearInfo.year}_${day.dayNumber}" },
                        ) { day ->
                            DayListItem(
                                dayNumber = day.dayNumber,
                                isSelected = yearInfo.year == selectedYear && day.dayNumber == selectedDay,
                                onClick = { onDaySelected(yearInfo.year, day.dayNumber) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun YearHeader(
    year: Int,
    dayCount: Int,
    isExpanded: Boolean,
    onToggle: () -> Unit,
) {
    FilledTonalButton(
        onClick = onToggle,
        modifier = Modifier.fillMaxWidth(),
        colors =
            ButtonDefaults.filledTonalButtonColors(
                containerColor =
                    if (isExpanded) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    },
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    imageVector =
                        if (isExpanded) {
                            Icons.Default.KeyboardArrowDown
                        } else {
                            Icons.AutoMirrored.Filled.KeyboardArrowRight
                        },
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = "$year",
                    style = MaterialTheme.typography.titleMedium,
                )
            }
            Surface(
                shape = MaterialTheme.shapes.extraSmall,
                color = MaterialTheme.colorScheme.secondaryContainer,
            ) {
                Text(
                    text = "$dayCount",
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
            }
        }
    }
}

@Composable
fun DayListItem(
    dayNumber: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(start = 24.dp),
        colors =
            ButtonDefaults.filledTonalButtonColors(
                containerColor =
                    if (isSelected) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("Day $dayNumber")
            Text("✓", color = MaterialTheme.colorScheme.primary)
        }
    }
}
