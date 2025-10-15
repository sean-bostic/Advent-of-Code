package aoc_2024.days


import core.Day

class Day15 : Day(15) {
    override fun part1(input: List<String>): Any {
        val (warehouse, moves) = parseInput(input)

        moves.forEach { move ->
            warehouse.executeMove(move)
        }

        return warehouse.calculateGPS()
    }

    override fun part2(input: List<String>): Any {
        val (warehouse, moves) = parseInputWide(input)

        moves.forEach { move ->
            warehouse.executeMove(move)
        }

        return warehouse.calculateGPS()
    }

    private data class Position(val x: Int, val y: Int) {
        operator fun plus(other: Position) = Position(x + other.x, y + other.y)
    }

    private class Warehouse(
        var robot: Position,
        val boxes: MutableSet<Position>,
        val walls: Set<Position>
    ) {
        fun executeMove(direction: Char) {
            val delta = when (direction) {
                '^' -> Position(0, -1)
                'v' -> Position(0, 1)
                '<' -> Position(-1, 0)
                '>' -> Position(1, 0)
                else -> return
            }

            val newRobotPos = robot + delta

            if (newRobotPos in walls) return

            if (newRobotPos in boxes) {
                if (canPushBoxes(newRobotPos, delta)) {
                    pushBoxes(newRobotPos, delta)
                    robot = newRobotPos
                }
            } else {
                robot = newRobotPos
            }
        }

        private fun canPushBoxes(startBox: Position, delta: Position): Boolean {
            var current = startBox

            while (current in boxes) {
                current += delta
            }

            return current !in walls
        }

        private fun pushBoxes(startBox: Position, delta: Position) {
            val boxesToMove = mutableListOf<Position>()
            var current = startBox

            while (current in boxes) {
                boxesToMove.add(current)
                current += delta
            }

            boxesToMove.forEach { box ->
                boxes.remove(box)
            }
            boxesToMove.forEach { box ->
                boxes.add(box + delta)
            }
        }

        fun calculateGPS(): Int {
            return boxes.sumOf { box ->
                100 * box.y + box.x
            }
        }
    }

    private fun parseInput(input: List<String>): Pair<Warehouse, String> {
        val separatorIndex = input.indexOfFirst { it.isEmpty() }
        val mapLines = input.subList(0, separatorIndex)
        val moveLines = input.subList(separatorIndex + 1, input.size)

        var robot = Position(0, 0)
        val boxes = mutableSetOf<Position>()
        val walls = mutableSetOf<Position>()

        mapLines.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                val pos = Position(x, y)
                when (char) {
                    '@' -> robot = pos
                    'O' -> boxes.add(pos)
                    '#' -> walls.add(pos)
                }
            }
        }

        val moves = moveLines.joinToString("")

        return Warehouse(robot, boxes, walls) to moves
    }

    private fun parseInputWide(input: List<String>): Pair<WideWarehouse, String> {
        val separatorIndex = input.indexOfFirst { it.isEmpty() }
        val mapLines = input.subList(0, separatorIndex)
        val moveLines = input.subList(separatorIndex + 1, input.size)

        var robot = Position(0, 0)
        val boxes = mutableSetOf<Position>()
        val walls = mutableSetOf<Position>()

        mapLines.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->
                val doubleX = x * 2
                when (char) {
                    '@' -> robot = Position(doubleX, y)
                    'O' -> boxes.add(Position(doubleX, y))
                    '#' -> {
                        walls.add(Position(doubleX, y))
                        walls.add(Position(doubleX + 1, y))
                    }
                }
            }
        }

        val moves = moveLines.joinToString("")

        return WideWarehouse(robot, boxes, walls) to moves
    }

    private class WideWarehouse(
        var robot: Position,
        val boxes: MutableSet<Position>,
        val walls: Set<Position>
    ) {
        fun executeMove(direction: Char) {
            val delta = when (direction) {
                '^' -> Position(0, -1)
                'v' -> Position(0, 1)
                '<' -> Position(-1, 0)
                '>' -> Position(1, 0)
                else -> return
            }

            val newRobotPos = robot + delta

            if (newRobotPos in walls) return

            val boxAtNewPos = when {
                newRobotPos in boxes -> newRobotPos
                Position(newRobotPos.x - 1, newRobotPos.y) in boxes ->
                    Position(newRobotPos.x - 1, newRobotPos.y)
                else -> null
            }

            if (boxAtNewPos != null) {
                if (direction == '<' || direction == '>') {
                    if (canPushBoxesHorizontal(boxAtNewPos, delta)) {
                        pushBoxesHorizontal(boxAtNewPos, delta)
                        robot = newRobotPos
                    }
                } else {
                    val boxesToPush = findBoxesToPushVertical(boxAtNewPos, delta)
                    if (boxesToPush != null && canPushBoxesVertical(boxesToPush, delta)) {
                        pushBoxesVertical(boxesToPush, delta)
                        robot = newRobotPos
                    }
                }
            } else {
                robot = newRobotPos
            }
        }

        private fun canPushBoxesHorizontal(startBox: Position, delta: Position): Boolean {
            if (delta.x > 0) {
                var current = startBox
                while (current in boxes) {
                    current = Position(current.x + 2, current.y)
                }

                return current !in walls
            } else {
                var current = startBox
                while (Position(current.x - 2, current.y) in boxes) {
                    current = Position(current.x - 2, current.y)
                }
                return Position(current.x - 1, current.y) !in walls
            }
        }

        private fun pushBoxesHorizontal(startBox: Position, delta: Position) {
            val boxesToMove = mutableListOf<Position>()

            if (delta.x > 0) {
                var current = startBox
                while (current in boxes) {
                    boxesToMove.add(current)
                    current = Position(current.x + 2, current.y)
                }
            } else {
                var current = startBox
                boxesToMove.add(current)
                while (Position(current.x - 2, current.y) in boxes) {
                    current = Position(current.x - 2, current.y)
                    boxesToMove.add(current)
                }
            }

            boxesToMove.forEach { box -> boxes.remove(box) }
            boxesToMove.forEach { box -> boxes.add(box + delta) }
        }

        private fun findBoxesToPushVertical(startBox: Position, delta: Position): Set<Position>? {
            val result = mutableSetOf<Position>()
            val queue = ArrayDeque<Position>()
            queue.add(startBox)

            while (queue.isNotEmpty()) {
                val box = queue.removeFirst()
                if (box in result) continue
                result.add(box)

                val leftNext = Position(box.x, box.y + delta.y)
                val rightNext = Position(box.x + 1, box.y + delta.y)

                if (leftNext in walls || rightNext in walls) return null

                if (leftNext in boxes) queue.add(leftNext)
                if (rightNext in boxes) queue.add(rightNext)
                if (Position(leftNext.x - 1, leftNext.y) in boxes) {
                    queue.add(Position(leftNext.x - 1, leftNext.y))
                }
            }

            return result
        }

        private fun canPushBoxesVertical(boxesToPush: Set<Position>, delta: Position): Boolean {
            return true
        }

        private fun pushBoxesVertical(boxesToPush: Set<Position>, delta: Position) {
            boxesToPush.forEach { box -> boxes.remove(box) }
            boxesToPush.forEach { box -> boxes.add(box + delta) }
        }

        fun calculateGPS(): Int {
            return boxes.sumOf { box ->
                100 * box.y + box.x
            }
        }
    }
}
