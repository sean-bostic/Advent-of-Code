package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.YearRegistry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import kotlin.system.measureTimeMillis

@Composable
fun DayDetail(year: Int, dayNumber: Int) {
    val day = remember(year, dayNumber) { YearRegistry.getDay(year, dayNumber) }

    if (day == null) {
        NotImplementedScreen(year, dayNumber)
        return
    }

    var part1Result by remember(year, dayNumber) { mutableStateOf<String?>(null) }
    var part2Result by remember(year, dayNumber) { mutableStateOf<String?>(null) }
    var part1Time by remember(year, dayNumber) { mutableStateOf<Long?>(null) }
    var part2Time by remember(year, dayNumber) { mutableStateOf<Long?>(null) }
    var isRunning by remember(year, dayNumber) { mutableStateOf(false) }
    var error by remember(year, dayNumber) { mutableStateOf<String?>(null) }
    var showCodeViewer by remember(year, dayNumber) { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Day $dayNumber",
                style = MaterialTheme.typography.headlineLarge
            )
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Text(
                    text = "$year",
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        isRunning = true
                        error = null
                        part1Result = null
                        part2Result = null
                        part1Time = null
                        part2Time = null

                        try {
                            val input = withContext(Dispatchers.IO) {
                                day.loadInput(year)
                            }

                            val time1 = measureTimeMillis {
                                part1Result = day.part1(input).toString()
                            }
                            part1Time = time1

                            val time2 = measureTimeMillis {
                                part2Result = day.part2(input).toString()
                            }
                            part2Time = time2

                        } catch (e: Exception) {
                            error = e.message ?: "Unknown error"
                            e.printStackTrace()
                        } finally {
                            isRunning = false
                        }
                    }
                },
                enabled = !isRunning
            ) {
                Text(if (isRunning) "Running..." else "Run Solutions")
            }

            OutlinedButton(
                onClick = { showCodeViewer = true }
            ) {
                Text("View Code")
            }
        }

        error?.let {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = "Error: $it",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ResultCard(
                title = "Part 1",
                result = part1Result,
                time = part1Time,
                modifier = Modifier.weight(1f)
            )

            ResultCard(
                title = "Part 2",
                result = part2Result,
                time = part2Time,
                modifier = Modifier.weight(1f)
            )
        }
    }

    if (showCodeViewer) {
        CodeViewerDialog(
            year = year,
            dayNumber = dayNumber,
            onDismiss = { showCodeViewer = false }
        )
    }
}

@Composable
fun ResultCard(
    title: String,
    result: String?,
    time: Long?,
    modifier: Modifier = Modifier
) {
    var showCopiedMessage by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            if (result != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = result,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        time?.let {
                            Text(
                                text = "⏱️ ${it}ms",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    TextButton(
                        onClick = {
                            copyToClipboard(result)
                            showCopiedMessage = true
                        }
                    ) {
                        Text("📋 Copy")
                    }
                }

                if (showCopiedMessage) {
                    Text(
                        text = "✓ Copied!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )

                    LaunchedEffect(Unit) {
                        delay(2000)
                        showCopiedMessage = false
                    }
                }

            } else {
                Text(
                    text = "Not run yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

private fun copyToClipboard(text: String) {
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val selection = StringSelection(text)
    clipboard.setContents(selection, selection)
}

@Composable
fun NotImplementedScreen(year: Int, dayNumber: Int) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Day $dayNumber",
                style = MaterialTheme.typography.headlineLarge
            )
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Text(
                    text = "$year",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Not implemented yet",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}