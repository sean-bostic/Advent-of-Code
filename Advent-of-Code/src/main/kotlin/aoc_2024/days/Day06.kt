package aoc_2024.days

import core.Day

class Day06 : Day(6) {
    override fun part1(input: List<String>): Any {
        val (guard, obstacles, width, height) = parseMap(input)
        val visited = simulateGuard(guard, obstacles, width, height)
        return visited.size
    }

    override fun part2(input: List<String>): Any {
        val (guard, obstacles, width, height) = parseMap(input)

        var loopCount = 0

        for (y in 0 until height) {
            for (x in 0 until width) {
                val pos = Position(x, y)

                if (pos == guard.pos || pos in obstacles) continue

                val newObstacles = obstacles + pos

                if (causesLoop(guard, newObstacles, width, height)) {
                    loopCount++
                }
            }
        }

        return loopCount
    }

    private data class Position(val x: Int, val y: Int)
    private data class Guard(val pos: Position, val dir: Direction)

    private enum class Direction(val dx: Int, val dy: Int) {
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0);

        fun turnRight(): Direction = when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }

    private fun parseMap(input: List<String>): ParseResult {
        val height = input.size
        val width = input[0].length
        val obstacles = mutableSetOf<Position>()
        var guard: Guard? = null

        for (y in input.indices) {
            for (x in input[y].indices) {
                when (input[y][x]) {
                    '#' -> obstacles.add(Position(x, y))
                    '^' -> guard = Guard(Position(x, y), Direction.UP)
                    '>' -> guard = Guard(Position(x, y), Direction.RIGHT)
                    'v' -> guard = Guard(Position(x, y), Direction.DOWN)
                    '<' -> guard = Guard(Position(x, y), Direction.LEFT)
                }
            }
        }

        return ParseResult(guard!!, obstacles, width, height)
    }

    private fun simulateGuard(
        initialGuard: Guard,
        obstacles: Set<Position>,
        width: Int,
        height: Int
    ): Set<Position> {
        val visited = mutableSetOf<Position>()
        var guard = initialGuard

        while (guard.pos.x in 0 until width && guard.pos.y in 0 until height) {
            visited.add(guard.pos)

            val nextPos = Position(
                guard.pos.x + guard.dir.dx,
                guard.pos.y + guard.dir.dy
            )

            guard = if (nextPos in obstacles) {
                Guard(guard.pos, guard.dir.turnRight())
            } else {
                Guard(nextPos, guard.dir)
            }
        }

        return visited
    }

    private fun causesLoop(
        initialGuard: Guard,
        obstacles: Set<Position>,
        width: Int,
        height: Int
    ): Boolean {
        val visitedStates = mutableSetOf<Guard>()
        var guard = initialGuard

        while (guard.pos.x in 0 until width && guard.pos.y in 0 until height) {
            if (guard in visitedStates) {
                return true
            }
            visitedStates.add(guard)

            val nextPos = Position(
                guard.pos.x + guard.dir.dx,
                guard.pos.y + guard.dir.dy
            )

            guard = if (nextPos in obstacles) {
                Guard(guard.pos, guard.dir.turnRight())
            } else {
                Guard(nextPos, guard.dir)
            }
        }

        return false
    }

    private data class ParseResult(
        val guard: Guard,
        val obstacles: Set<Position>,
        val width: Int,
        val height: Int
    )
}