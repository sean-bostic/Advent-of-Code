package aoc_2024.days

import core.Day

class Day12 : Day(12) {
    override fun part1(input: List<String>): Any {
        val grid = input.map { it.toCharArray() }
        val regions = findAllRegions(grid)

        return regions.sumOf { region ->
            val area = region.size
            val perimeter = calculatePerimeter(region, grid)
            area * perimeter
        }
    }

    override fun part2(input: List<String>): Any {
        val grid = input.map { it.toCharArray() }
        val regions = findAllRegions(grid)

        return regions.sumOf { region ->
            val area = region.size
            val sides = calculateSides(region)
            area * sides
        }
    }

    private data class Position(val x: Int, val y: Int)

    private fun findAllRegions(grid: List<CharArray>): List<Set<Position>> {
        val height = grid.size
        val width = grid[0].size
        val visited = Array(height) { BooleanArray(width) }
        val regions = mutableListOf<Set<Position>>()

        for (y in 0 until height) {
            for (x in 0 until width) {
                if (!visited[y][x]) {
                    val region = floodFill(grid, x, y, visited)
                    if (region.isNotEmpty()) {
                        regions.add(region)
                    }
                }
            }
        }

        return regions
    }

    private fun floodFill(
        grid: List<CharArray>,
        startX: Int,
        startY: Int,
        visited: Array<BooleanArray>
    ): Set<Position> {
        val height = grid.size
        val width = grid[0].size
        val plantType = grid[startY][startX]
        val region = mutableSetOf<Position>()
        val queue = ArrayDeque<Position>()

        queue.add(Position(startX, startY))
        visited[startY][startX] = true

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            region.add(current)

            val directions = listOf(
                Position(0, -1),  // up
                Position(0, 1),   // down
                Position(-1, 0),  // left
                Position(1, 0)    // right
            )

            for (dir in directions) {
                val next = Position(current.x + dir.x, current.y + dir.y)

                if (next.x !in 0 until width || next.y !in 0 until height) continue

                if (visited[next.y][next.x]) continue

                if (grid[next.y][next.x] == plantType) {
                    visited[next.y][next.x] = true
                    queue.add(next)
                }
            }
        }

        return region
    }

    private fun calculatePerimeter(region: Set<Position>, grid: List<CharArray>): Int {
        val height = grid.size
        val width = grid[0].size
        var perimeter = 0

        for (plot in region) {

            val directions = listOf(
                Position(0, -1),  // up
                Position(0, 1),   // down
                Position(-1, 0),  // left
                Position(1, 0)    // right
            )

            for (dir in directions) {
                val neighbor = Position(plot.x + dir.x, plot.y + dir.y)

                if (neighbor.x !in 0 until width ||
                    neighbor.y !in 0 until height ||
                    neighbor !in region) {
                    perimeter++
                }
            }
        }

        return perimeter
    }

    private fun calculateSides(region: Set<Position>): Int {

        var corners = 0

        for (plot in region) {
            corners += countCorners(plot, region)
        }

        return corners
    }

    private fun countCorners(plot: Position, region: Set<Position>): Int {
        var corners = 0
        val (x, y) = plot

        val up = Position(x, y - 1) in region
        val down = Position(x, y + 1) in region
        val left = Position(x - 1, y) in region
        val right = Position(x + 1, y) in region
        val upLeft = Position(x - 1, y - 1) in region
        val upRight = Position(x + 1, y - 1) in region
        val downLeft = Position(x - 1, y + 1) in region
        val downRight = Position(x + 1, y + 1) in region

        if (!up && !left) corners++      // Top-left outer
        if (!up && !right) corners++     // Top-right outer
        if (!down && !left) corners++    // Bottom-left outer
        if (!down && !right) corners++   // Bottom-right outer

        if (up && left && !upLeft) corners++       // Top-left inner
        if (up && right && !upRight) corners++     // Top-right inner
        if (down && left && !downLeft) corners++   // Bottom-left inner
        if (down && right && !downRight) corners++ // Bottom-right inner

        return corners
    }
}
