package aoc_2024.days

import core.Day

class Day10 : Day(10) {
    override fun part1(input: List<String>): Any {
        val map = parseMap(input)
        val trailheads = findTrailheads(map)

        return trailheads.sumOf { trailhead ->
            calculateTrailheadScore(map, trailhead)
        }
    }

    override fun part2(input: List<String>): Any {
        val map = parseMap(input)
        val trailheads = findTrailheads(map)

        return trailheads.sumOf { trailhead ->
            calculateTrailheadRating(map, trailhead)
        }
    }

    private data class Position(val x: Int, val y: Int)

    private fun parseMap(input: List<String>): List<List<Int>> {
        return input.map { row ->
            row.map { it.digitToInt() }
        }
    }

    private fun findTrailheads(map: List<List<Int>>): List<Position> {
        val trailheads = mutableListOf<Position>()

        for (y in map.indices) {
            for (x in map[y].indices) {
                if (map[y][x] == 0) {
                    trailheads.add(Position(x, y))
                }
            }
        }

        return trailheads
    }

    private fun calculateTrailheadScore(map: List<List<Int>>, start: Position): Int {
        val height = map.size
        val width = map[0].size
        val reachableNines = mutableSetOf<Position>()

        val queue = ArrayDeque<Position>()
        val visited = mutableSetOf<Position>()

        queue.add(start)
        visited.add(start)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val currentHeight = map[current.y][current.x]

            if (currentHeight == 9) {
                reachableNines.add(current)
                continue
            }

            val directions = listOf(
                Position(0, -1),  // up
                Position(0, 1),   // down
                Position(-1, 0),  // left
                Position(1, 0)    // right
            )

            for (dir in directions) {
                val next = Position(current.x + dir.x, current.y + dir.y)

                // Check bounds
                if (next.x !in 0 until width || next.y !in 0 until height) continue

                // Check if already visited
                if (next in visited) continue

                // Check if height increases by exactly 1
                val nextHeight = map[next.y][next.x]
                if (nextHeight == currentHeight + 1) {
                    queue.add(next)
                    visited.add(next)
                }
            }
        }

        return reachableNines.size
    }

    private fun calculateTrailheadRating(map: List<List<Int>>, start: Position): Int {
        return countPaths(map, start, map[start.y][start.x])
    }

    private fun countPaths(map: List<List<Int>>, current: Position, currentHeight: Int): Int {
        val height = map.size
        val width = map[0].size

        if (currentHeight == 9) {
            return 1
        }

        var totalPaths = 0

        val directions = listOf(
            Position(0, -1),
            Position(0, 1),
            Position(-1, 0),
            Position(1, 0)
        )

        for (dir in directions) {
            val next = Position(current.x + dir.x, current.y + dir.y)

            if (next.x !in 0 until width || next.y !in 0 until height) continue

            val nextHeight = map[next.y][next.x]
            if (nextHeight == currentHeight + 1) {
                totalPaths += countPaths(map, next, nextHeight)
            }
        }

        return totalPaths
    }
}
