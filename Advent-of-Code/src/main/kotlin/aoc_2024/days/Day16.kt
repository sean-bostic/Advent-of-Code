package aoc_2024.days

import core.Day

import java.util.PriorityQueue

class Day16 : Day(16) {
    override fun part1(input: List<String>): Any {
        val maze = parseMaze(input)
        return findLowestScore(maze)
    }

    override fun part2(input: List<String>): Any {
        val maze = parseMaze(input)
        return findBestPathTiles(maze)
    }

    private data class Position(val x: Int, val y: Int)

    private enum class Direction(val dx: Int, val dy: Int) {
        NORTH(0, -1),
        EAST(1, 0),
        SOUTH(0, 1),
        WEST(-1, 0);

        fun turnClockwise() = when (this) {
            NORTH -> EAST
            EAST -> SOUTH
            SOUTH -> WEST
            WEST -> NORTH
        }

        fun turnCounterClockwise() = when (this) {
            NORTH -> WEST
            WEST -> SOUTH
            SOUTH -> EAST
            EAST -> NORTH
        }
    }

    private data class State(
        val position: Position,
        val direction: Direction,
        val score: Int
    ) : Comparable<State> {
        override fun compareTo(other: State) = score.compareTo(other.score)
    }

    private data class Maze(
        val start: Position,
        val end: Position,
        val walls: Set<Position>
    )

    private fun parseMaze(input: List<String>): Maze {
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

        return Maze(start, end, walls)
    }

    private fun findLowestScore(maze: Maze): Int {
        val pq = PriorityQueue<State>()
        val visited = mutableSetOf<Pair<Position, Direction>>()

        pq.add(State(maze.start, Direction.EAST, 0))

        while (pq.isNotEmpty()) {
            val current = pq.poll()

            if (current.position == maze.end) {
                return current.score
            }

            val stateKey = current.position to current.direction
            if (stateKey in visited) continue
            visited.add(stateKey)

            val nextPos = Position(
                current.position.x + current.direction.dx,
                current.position.y + current.direction.dy
            )

            if (nextPos !in maze.walls) {
                pq.add(State(nextPos, current.direction, current.score + 1))
            }

            pq.add(State(
                current.position,
                current.direction.turnClockwise(),
                current.score + 1000
            ))

            pq.add(State(
                current.position,
                current.direction.turnCounterClockwise(),
                current.score + 1000
            ))
        }

        return -1
    }

    private fun findBestPathTiles(maze: Maze): Int {
        val pq = PriorityQueue<State>()
        val scores = mutableMapOf<Pair<Position, Direction>, Int>()
        val predecessors = mutableMapOf<Pair<Position, Direction>, MutableSet<Pair<Position, Direction>>>()

        val startState = maze.start to Direction.EAST
        pq.add(State(maze.start, Direction.EAST, 0))
        scores[startState] = 0

        var bestScore = Int.MAX_VALUE
        val endStates = mutableSetOf<Pair<Position, Direction>>()

        while (pq.isNotEmpty()) {
            val current = pq.poll()
            val currentKey = current.position to current.direction

            if (current.score > scores.getOrDefault(currentKey, Int.MAX_VALUE)) continue

            if (current.position == maze.end) {
                if (current.score < bestScore) {
                    bestScore = current.score
                    endStates.clear()
                    endStates.add(currentKey)
                } else if (current.score == bestScore) {
                    endStates.add(currentKey)
                }
                continue
            }

            val nextPos = Position(
                current.position.x + current.direction.dx,
                current.position.y + current.direction.dy
            )

            if (nextPos !in maze.walls) {
                val nextKey = nextPos to current.direction
                val nextScore = current.score + 1

                if (nextScore <= scores.getOrDefault(nextKey, Int.MAX_VALUE)) {
                    if (nextScore < scores.getOrDefault(nextKey, Int.MAX_VALUE)) {
                        scores[nextKey] = nextScore
                        predecessors[nextKey] = mutableSetOf()
                        pq.add(State(nextPos, current.direction, nextScore))
                    }
                    predecessors.getOrPut(nextKey) { mutableSetOf() }.add(currentKey)
                }
            }

            val cwDir = current.direction.turnClockwise()
            val cwKey = current.position to cwDir
            val cwScore = current.score + 1000

            if (cwScore <= scores.getOrDefault(cwKey, Int.MAX_VALUE)) {
                if (cwScore < scores.getOrDefault(cwKey, Int.MAX_VALUE)) {
                    scores[cwKey] = cwScore
                    predecessors[cwKey] = mutableSetOf()
                    pq.add(State(current.position, cwDir, cwScore))
                }
                predecessors.getOrPut(cwKey) { mutableSetOf() }.add(currentKey)
            }

            val ccwDir = current.direction.turnCounterClockwise()
            val ccwKey = current.position to ccwDir
            val ccwScore = current.score + 1000

            if (ccwScore <= scores.getOrDefault(ccwKey, Int.MAX_VALUE)) {
                if (ccwScore < scores.getOrDefault(ccwKey, Int.MAX_VALUE)) {
                    scores[ccwKey] = ccwScore
                    predecessors[ccwKey] = mutableSetOf()
                    pq.add(State(current.position, ccwDir, ccwScore))
                }
                predecessors.getOrPut(ccwKey) { mutableSetOf() }.add(currentKey)
            }
        }

        val tilesOnBestPaths = mutableSetOf<Position>()
        val queue = ArrayDeque(endStates)
        val visitedStates = mutableSetOf<Pair<Position, Direction>>()

        while (queue.isNotEmpty()) {
            val state = queue.removeFirst()
            if (state in visitedStates) continue
            visitedStates.add(state)

            tilesOnBestPaths.add(state.first)

            predecessors[state]?.forEach { pred ->
                queue.add(pred)
            }
        }

        return tilesOnBestPaths.size
    }
}
