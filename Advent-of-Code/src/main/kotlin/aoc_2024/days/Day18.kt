package aoc_2024.days

import core.Day

class Day18 : Day(18) {
    override fun part1(input: List<String>): Any {
        val bytes = parseBytes(input)

        val (gridSize, numBytes) = if (bytes.size < 30) {
            7 to 12
        } else {
            71 to 1024
        }

        val corrupted = bytes.take(numBytes).toSet()

        return findShortestPath(gridSize, corrupted) ?: -1
    }

    override fun part2(input: List<String>): Any {
        val bytes = parseBytes(input)

        val gridSize = if (bytes.size < 30) 7 else 71

        var left = 0
        var right = bytes.size - 1
        var blockingByte: Position? = null

        while (left <= right) {
            val mid = (left + right) / 2
            val corrupted = bytes.take(mid + 1).toSet()

            if (findShortestPath(gridSize, corrupted) == null) {
                blockingByte = bytes[mid]
                right = mid - 1
            } else {
                left = mid + 1
            }
        }

        return blockingByte?.let { "${it.x},${it.y}" } ?: ""
    }

    private data class Position(val x: Int, val y: Int)

    private fun parseBytes(input: List<String>): List<Position> {
        return input.map { line ->
            val (x, y) = line.split(",").map { it.toInt() }
            Position(x, y)
        }
    }

    private fun findShortestPath(gridSize: Int, corrupted: Set<Position>): Int? {
        val start = Position(0, 0)
        val end = Position(gridSize - 1, gridSize - 1)

        val queue = ArrayDeque<Pair<Position, Int>>()
        val visited = mutableSetOf<Position>()

        queue.add(start to 0)
        visited.add(start)

        val directions = listOf(
            Position(0, -1),  // up
            Position(0, 1),   // down
            Position(-1, 0),  // left
            Position(1, 0)    // right
        )

        while (queue.isNotEmpty()) {
            val (current, steps) = queue.removeFirst()

            if (current == end) {
                return steps
            }

            for (dir in directions) {
                val next = Position(current.x + dir.x, current.y + dir.y)

                if (next.x !in 0 until gridSize || next.y !in 0 until gridSize) continue

                if (next in corrupted || next in visited) continue

                visited.add(next)
                queue.add(next to steps + 1)
            }
        }

        return null
    }
}
