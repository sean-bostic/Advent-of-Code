package aoc_2024.days

import core.Day

class Day14 : Day(14) {
    override fun part1(input: List<String>): Any {
        val robots = input.mapNotNull { it.toRobot() }


        val (width, height) = if (robots.size < 20) {
            11 to 7
        } else {
            101 to 103
        }

        val seconds = 100

        val finalPositions = robots.map { robot ->
            robot.positionAfter(seconds, width, height)
        }

        return calculateSafetyFactor(finalPositions, width, height)
    }

    override fun part2(input: List<String>): Any {
        val robots = input.mapNotNull { it.toRobot() }
        val width = 101
        val height = 103

        var minSafetyFactor = Int.MAX_VALUE
        var minSafetySecond = 0

        for (seconds in 0..10000) {
            val positions = robots.map { it.positionAfter(seconds, width, height) }

            if (positions.distinct().size == positions.size) {
                val safetyFactor = calculateSafetyFactor(positions, width, height)
                if (safetyFactor < minSafetyFactor) {
                    minSafetyFactor = safetyFactor
                    minSafetySecond = seconds
                }
            }
        }

        return minSafetySecond
    }

    private data class Robot(
        val position: Pair<Int, Int>,
        val velocity: Pair<Int, Int>
    ) {
        fun positionAfter(seconds: Int, width: Int, height: Int): Pair<Int, Int> {
            val (px, py) = position
            val (vx, vy) = velocity

            var newX = (px + vx * seconds) % width
            var newY = (py + vy * seconds) % height

            if (newX < 0) newX += width
            if (newY < 0) newY += height

            return newX to newY
        }
    }

    private fun String.toRobot(): Robot? {
        val regex = """p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)""".toRegex()
        val match = regex.find(this) ?: return null
        val (px, py, vx, vy) = match.destructured

        return Robot(
            position = px.toInt() to py.toInt(),
            velocity = vx.toInt() to vy.toInt()
        )
    }

    private fun calculateSafetyFactor(
        positions: List<Pair<Int, Int>>,
        width: Int,
        height: Int
    ): Int {
        val midX = width / 2
        val midY = height / 2

        val quadrants = positions
            .filterNot { (x, y) -> x == midX || y == midY }
            .groupBy { (x, y) ->
                when {
                    x < midX && y < midY -> 0
                    x > midX && y < midY -> 1
                    x < midX && y > midY -> 2
                    else -> 3
                }
            }
            .mapValues { it.value.size }

        return (0..3).fold(1) { acc, quadrant ->
            acc * (quadrants[quadrant] ?: 0)
        }
    }

    // For debugging
    private fun visualizeGrid(positions: List<Pair<Int, Int>>, width: Int, height: Int): String {
        val grid = Array(height) { CharArray(width) { '.' } }
        val positionCounts = positions.groupingBy { it }.eachCount()

        for ((pos, count) in positionCounts) {
            val (x, y) = pos
            if (x in 0 until width && y in 0 until height) {
                grid[y][x] = if (count > 9) '#' else count.digitToChar()
            }
        }

        return grid.joinToString("\n") { it.concatToString() }
    }
}