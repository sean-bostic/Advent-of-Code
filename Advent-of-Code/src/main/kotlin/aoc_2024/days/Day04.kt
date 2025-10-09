package aoc_2024.days
import core.Day

class Day04 : Day(4) {
    override fun part1(input: List<String>): Any {
        return input.countXMAS()
    }

    override fun part2(input: List<String>): Any {
        return input.countXMASPatterns()
    }
}
data class Direction(val row: Int, val col: Int)

private fun List<String>.countXMAS(): Int {
    val target = "XMAS"

    val directions = listOf(
        Direction(0, 1),
        Direction(0, -1),
        Direction(1, 0),
        Direction(-1, 0),
        Direction(1, 1),
        Direction(1, -1),
        Direction(-1, 1),
        Direction(-1, -1)
    )

    fun hasWordAt(row: Int, col: Int, direction: Direction): Boolean =
        target.indices.all { i ->
            val r = row + direction.row * i
            val c = col + direction.col * i
            r in indices && c in this[0].indices && this[r][c] == target[i]
        }

    return indices.sumOf { row ->
        this[0].indices.sumOf { col ->
            directions.count { dir -> hasWordAt(row, col, dir) }
        }
    }
}

private fun List<String>.countXMASPatterns(): Int {
    fun isValidXMAS(row: Int, col: Int): Boolean {
        // Check bounds - need space for the X pattern
        if (row !in 1 until size - 1 || col !in 1 until this[0].length - 1) return false

        // Center must be 'A'
        if (this[row][col] != 'A') return false

        // Get the four corners of the X
        val topLeft = this[row - 1][col - 1]
        val topRight = this[row - 1][col + 1]
        val bottomLeft = this[row + 1][col - 1]
        val bottomRight = this[row + 1][col + 1]

        // Check if both diagonals form "MAS" or "SAM"
        val diagonal1Valid = (topLeft == 'M' && bottomRight == 'S') ||
                (topLeft == 'S' && bottomRight == 'M')

        val diagonal2Valid = (topRight == 'M' && bottomLeft == 'S') ||
                (topRight == 'S' && bottomLeft == 'M')

        return diagonal1Valid && diagonal2Valid
    }

    return indices.sumOf { row ->
        this[0].indices.count { col -> isValidXMAS(row, col) }
    }
}


