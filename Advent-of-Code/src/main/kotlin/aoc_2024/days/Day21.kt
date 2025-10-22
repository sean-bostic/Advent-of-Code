package aoc_2024.days

import core.Day

class Day21 : Day(21) {
    override fun part1(input: List<String>): Any {
        return input.sumOf { code ->
            val length = findShortestSequence(code, 2)
            val numericPart = code.filter { it.isDigit() }.toInt()
            length * numericPart
        }
    }

    override fun part2(input: List<String>): Any {
        return input.sumOf { code ->
            val length = findShortestSequence(code, 25)
            val numericPart = code.filter { it.isDigit() }.toInt()
            length * numericPart
        }
    }

    private data class Position(val x: Int, val y: Int)

    private val numericKeypad =
        mapOf(
            '7' to Position(0, 0), '8' to Position(1, 0), '9' to Position(2, 0),
            '4' to Position(0, 1), '5' to Position(1, 1), '6' to Position(2, 1),
            '1' to Position(0, 2), '2' to Position(1, 2), '3' to Position(2, 2),
            '0' to Position(1, 3), 'A' to Position(2, 3),
        )
    private val numericGap = Position(0, 3)

    private val directionalKeypad =
        mapOf(
            '^' to Position(1, 0),
            'A' to Position(2, 0),
            '<' to Position(0, 1),
            'v' to Position(1, 1),
            '>' to Position(2, 1),
        )
    private val directionalGap = Position(0, 0)

    private fun findShortestSequence(
        code: String,
        robotLayers: Int,
    ): Long {
        val memo = mutableMapOf<Triple<String, Int, Boolean>, Long>()

        fun solve(
            sequence: String,
            depth: Int,
            isNumeric: Boolean,
        ): Long {
            if (depth == 0) return sequence.length.toLong()

            val key = Triple(sequence, depth, isNumeric)
            if (key in memo) return memo[key]!!

            val keypad = if (isNumeric) numericKeypad else directionalKeypad
            val gap = if (isNumeric) numericGap else directionalGap

            var currentPos = keypad['A']!!
            var totalLength = 0L

            for (target in sequence) {
                val targetPos = keypad[target]!!
                val moves = getOptimalMoves(currentPos, targetPos, gap)

                val minLength =
                    moves.minOf { moveSeq ->
                        solve(moveSeq + 'A', depth - 1, false)
                    }

                totalLength += minLength
                currentPos = targetPos
            }

            memo[key] = totalLength
            return totalLength
        }

        return solve(code, robotLayers + 1, true)
    }

    private fun getOptimalMoves(
        from: Position,
        to: Position,
        gap: Position,
    ): List<String> {
        val dx = to.x - from.x
        val dy = to.y - from.y

        val horizontal =
            when {
                dx > 0 -> ">".repeat(dx)
                dx < 0 -> "<".repeat(-dx)
                else -> ""
            }

        val vertical =
            when {
                dy > 0 -> "v".repeat(dy)
                dy < 0 -> "^".repeat(-dy)
                else -> ""
            }

        if (dx == 0 || dy == 0) return listOf(horizontal + vertical)

        val results = mutableListOf<String>()

        val horizontalFirst = Position(to.x, from.y)
        if (horizontalFirst != gap) {
            results.add(horizontal + vertical)
        }

        val verticalFirst = Position(from.x, to.y)
        if (verticalFirst != gap) {
            results.add(vertical + horizontal)
        }

        return results.ifEmpty { listOf("") }
    }
}
