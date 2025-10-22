package aoc_2024.days

import core.Day

class Day11 : Day(11) {
    override fun part1(input: List<String>): Any {
        val stones = parseStones(input[0])
        return countStonesAfterBlinks(stones, 25)
    }

    override fun part2(input: List<String>): Any {
        val stones = parseStones(input[0])
        return countStonesAfterBlinks(stones, 75)
    }

    private fun parseStones(line: String): Map<Long, Long> {
        return line.split(" ")
            .map { it.toLong() }
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }
    }

    private fun countStonesAfterBlinks(
        initialStones: Map<Long, Long>,
        blinks: Int,
    ): Long {
        var stones = initialStones

        repeat(blinks) {
            stones = blink(stones)
        }

        return stones.values.sum()
    }

    private fun blink(stones: Map<Long, Long>): Map<Long, Long> {
        val newStones = mutableMapOf<Long, Long>()

        for ((value, count) in stones) {
            when {
                value == 0L -> {
                    newStones[1L] = newStones.getOrDefault(1L, 0) + count
                }
                hasEvenDigits(value) -> {
                    val (left, right) = splitNumber(value)
                    newStones[left] = newStones.getOrDefault(left, 0) + count
                    newStones[right] = newStones.getOrDefault(right, 0) + count
                }
                else -> {
                    val newValue = value * 2024
                    newStones[newValue] = newStones.getOrDefault(newValue, 0) + count
                }
            }
        }

        return newStones
    }

    private fun hasEvenDigits(n: Long): Boolean {
        val digitCount = n.toString().length
        return digitCount % 2 == 0
    }

    private fun splitNumber(n: Long): Pair<Long, Long> {
        val str = n.toString()
        val mid = str.length / 2
        val left = str.substring(0, mid).toLong()
        val right = str.substring(mid).toLong()
        return Pair(left, right)
    }
}
