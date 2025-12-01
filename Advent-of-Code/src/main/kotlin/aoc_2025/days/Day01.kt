package aoc_2025.days

import core.Day

class Day01 : Day(1) {
    override fun part1(input: List<String>): Any {
        var dial = 50
        var zeros = 0
        input.forEach {
            dial = if (it[0] == 'L') {
                (dial - it.drop(1).toInt()) % (100)
            } else {
                (dial + it.drop(1).toInt()) % (100)
            }
            if (dial == 0 ) zeros++
        }
        return zeros
    }

    override fun part2(input: List<String>): Any {
        var dial = 50
        var zeros = 0
        input.forEach {
            val change = if (it[0] == 'L') -1 else 1

            repeat(it.drop(1).toInt()) { dial = (dial + change) % (100)
            if (dial == 0 ) zeros++
            }
        }
        return zeros
    }
}