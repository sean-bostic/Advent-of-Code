package aoc_2024.days

import core.Day

class Day13 : Day(13) {
    override fun part1(input: List<String>): Any {
        return input.chunked(4)
            .mapNotNull { it.toMachine()?.solve(maxPresses = 100) }
            .sum()
            .toInt()
    }

    override fun part2(input: List<String>): Any {
        return input.chunked(4)
            .mapNotNull {
                it.toMachine()?.let { machine ->
                    machine.copy(
                        prize =
                            machine.prize.first + 10000000000000L to
                                machine.prize.second + 10000000000000L,
                    ).solve()
                } 
            }
            .sum()
            .toInt()
    }

    private data class Machine(
        val buttonA: Pair<Long, Long>,
        val buttonB: Pair<Long, Long>,
        val prize: Pair<Long, Long>,
    ) {
        fun solve(maxPresses: Long = Long.MAX_VALUE): Long? {
            val (ax, ay) = buttonA
            val (bx, by) = buttonB
            val (px, py) = prize

            val denominator = by * ax - bx * ay
            if (denominator == 0L) return null

            val b = (py * ax - px * ay) / denominator
            val a = (px - b * bx) / ax

            return when {
                a < 0 || b < 0 -> null
                a > maxPresses || b > maxPresses -> null
                (py * ax - px * ay) % denominator != 0L -> null
                (px - b * bx) % ax != 0L -> null
                a * ax + b * bx != px || a * ay + b * by != py -> null
                else -> a * 3 + b
            }
        }
    }

    private fun List<String>.toMachine(): Machine? {
        if (size < 3) return null

        val buttonARegex = """Button A: X\+(\d+), Y\+(\d+)""".toRegex()
        val buttonBRegex = """Button B: X\+(\d+), Y\+(\d+)""".toRegex()
        val prizeRegex = """Prize: X=(\d+), Y=(\d+)""".toRegex()

        val (ax, ay) = buttonARegex.find(this[0])?.destructured ?: return null
        val (bx, by) = buttonBRegex.find(this[1])?.destructured ?: return null
        val (px, py) = prizeRegex.find(this[2])?.destructured ?: return null

        return Machine(
            buttonA = ax.toLong() to ay.toLong(),
            buttonB = bx.toLong() to by.toLong(),
            prize = px.toLong() to py.toLong(),
        )
    }
}
