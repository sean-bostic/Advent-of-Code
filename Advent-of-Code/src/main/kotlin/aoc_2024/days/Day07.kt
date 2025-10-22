package aoc_2024.days

import core.Day

class Day07 : Day(7) {
    override fun part1(input: List<String>): Any {
        return input.sumOf { line ->
            val (testValue, numbers) = parseLine(line)
            if (canProduceValue(testValue, numbers, listOf('+', '*'))) {
                testValue
            } else {
                0L
            }
        }
    }

    override fun part2(input: List<String>): Any {
        return input.sumOf { line ->
            val (testValue, numbers) = parseLine(line)
            if (canProduceValue(testValue, numbers, listOf('+', '*', '|'))) {
                testValue
            } else {
                0L
            }
        }
    }

    private fun parseLine(line: String): Pair<Long, List<Long>> {
        val parts = line.split(": ")
        val testValue = parts[0].toLong()
        val numbers = parts[1].split(" ").map { it.toLong() }
        return testValue to numbers
    }

    private fun canProduceValue(
        target: Long,
        numbers: List<Long>,
        operators: List<Char>,
    ): Boolean {
        if (numbers.size == 1) {
            return numbers[0] == target
        }

        val operatorSlots = numbers.size - 1
        return tryAllCombinations(target, numbers, operators, operatorSlots, 0, numbers[0])
    }

    private fun tryAllCombinations(
        target: Long,
        numbers: List<Long>,
        operators: List<Char>,
        operatorSlots: Int,
        position: Int,
        current: Long,
    ): Boolean {
        if (position == operatorSlots) {
            return current == target
        }

        if (current > target) {
            return false
        }

        for (op in operators) {
            val nextNumber = numbers[position + 1]
            val result =
                when (op) {
                    '+' -> current + nextNumber
                    '*' -> current * nextNumber
                    '|' -> concatenate(current, nextNumber)
                    else -> current
                }

            if (tryAllCombinations(target, numbers, operators, operatorSlots, position + 1, result)) {
                return true
            }
        }

        return false
    }

    private fun concatenate(
        left: Long,
        right: Long,
    ): Long {
        return (left.toString() + right.toString()).toLong()
    }
}
