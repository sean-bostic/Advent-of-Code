package aoc_2024.days

import core.Day

class Day20 : Day(20) {
    override fun part1(input: List<String>): Any {
        val track = parseTrack(input)
        val path = findPath(track)
        val minSaving = if (input.size < 20) 1 else 100

        return findCheats(path, track, maxCheatTime = 2, minSaving)
    }

    override fun part2(input: List<String>): Any {
        val track = parseTrack(input)
        val path = findPath(track)
        val minSaving = if (input.size < 20) 50 else 100

        return findCheats(path, track, maxCheatTime = 20, minSaving)
    }

    private data class Position(val x: Int, val y: Int) {
        fun manhattanDistance(other: Position) = kotlin.math.abs(x - other.x) + kotlin.math.abs(y - other.y)
    }

    private data class Track(
        val start: Position,
        val end: Position,
        val walls: Set<Position>,
        val width: Int,
        val height: Int,
    )

    private fun parseTrack(input: List<String>): Track {
        var start = Position(0, 0)
        var end = Position(0, 0)
        val walls = mutableSetOf<Position>()

        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                val pos = Position(x, y)
                when (char) {
                    'S' -> start = pos
                    'E' -> end = pos
                    '#' -> walls.add(pos)
                }
            }
        }

        return Track(start, end, walls, input[0].length, input.size)
    }

    private fun findPath(track: Track): Map<Position, Int> {
        val distances = mutableMapOf<Position, Int>()
        val queue = ArrayDeque<Pair<Position, Int>>()

        queue.add(track.start to 0)
        distances[track.start] = 0

        val directions =
            listOf(
                Position(0, -1),
                Position(0, 1),
                Position(-1, 0),
                Position(1, 0),
            )

        while (queue.isNotEmpty()) {
            val (current, dist) = queue.removeFirst()

            if (current == track.end) continue

            for (dir in directions) {
                val next = Position(current.x + dir.x, current.y + dir.y)

                if (next in track.walls) continue
                if (next in distances) continue
                if (next.x !in 0 until track.width || next.y !in 0 until track.height) continue

                distances[next] = dist + 1
                queue.add(next to dist + 1)
            }
        }

        return distances
    }

    private fun findCheats(
        path: Map<Position, Int>,
        track: Track,
        maxCheatTime: Int,
        minSaving: Int,
    ): Int {
        var cheatCount = 0

        for ((start, startDist) in path) {
            for (dx in -maxCheatTime..maxCheatTime) {
                for (dy in -maxCheatTime..maxCheatTime) {
                    val cheatDist = kotlin.math.abs(dx) + kotlin.math.abs(dy)
                    if (cheatDist > maxCheatTime || cheatDist == 0) continue

                    val end = Position(start.x + dx, start.y + dy)

                    val endDist = path[end] ?: continue

                    if (endDist <= startDist) continue

                    val normalTime = endDist - startDist
                    val saved = normalTime - cheatDist

                    if (saved >= minSaving) {
                        cheatCount++
                    }
                }
            }
        }

        return cheatCount
    }
}
