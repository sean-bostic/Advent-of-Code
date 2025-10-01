package desktop.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StarProgress(
    completed: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf(1, 3, 5, 7, 9)
            .runningFold(0) { acc, stars -> acc + stars }
            .drop(1)
            .zip(listOf(1, 3, 5, 7, 9))
            .forEach { (starIndex, starsInRow) ->
                TreeRow(
                    starsInRow = starsInRow,
                    startIndex = starIndex - starsInRow + 1,
                    completed = completed
                )
            }

        // Small box for my crude tree trunk
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(24.dp)
                .height(50.dp)
                .background(
                    color = Color(0xFF8B4513),
                    shape = MaterialTheme.shapes.small
                )
        )
    }
}

@Composable
private fun TreeRow(
    starsInRow: Int,
    startIndex: Int,
    completed: Int
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(starsInRow) { offset ->
            val starNumber = startIndex + offset
            Star(isFilled = starNumber <= completed)
        }
    }
}

@Composable
private fun Star(isFilled: Boolean) {
    Icon(
        imageVector = if (isFilled) Icons.Filled.Star else Icons.Outlined.Star,
        contentDescription = if (isFilled) "Completed" else "Not completed",
        tint = if (isFilled)
            Color(0xFFFFD700)
        else
            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
        modifier = Modifier.size(32.dp)
    )
}