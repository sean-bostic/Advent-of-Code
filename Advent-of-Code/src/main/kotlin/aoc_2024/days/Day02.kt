package aoc_2024.days

import core.Day
import kotlin.math.abs

class Day02 : Day(2) {
    override fun part1(input: List<String>): Int {
        // Check each line and determine if levels are SAFE
        // SAFE if
        var safecount = 0

        input.forEach { line ->
            val levels = line.trim().split(" ").map { it.toInt() }
            if (isSafe(levels)) {
                safecount++
            }
        }
        return safecount
    }

    override fun part2(input: List<String>): Int {
        return input.count { line ->
            val levels = line.trim().split(" ").map { it.toInt() }
            isSafeWithDampener(levels)
        }
    }

    // 1.) all numbers increasing or all numbers decreasing
    // 2.) any two adjacent numbers must differ by atleast one and at most three
    private fun isSafe(levels: List<Int>): Boolean {
        val differences = levels.zipWithNext { a, b -> b - a }

        val sameDirection = differences.all { it > 0 } || differences.all { it < 0 }
        val validRange = differences.all { abs(it) in 1..3 }

        return sameDirection && validRange
    }

    private fun isSafeWithDampener(levels: List<Int>): Boolean {
        if (isSafe(levels)) return true

        // Brute force but remove numbers 1 at a time and check if safe
        for (i in levels.indices) {
            val modifiedLevels = levels.filterIndexed { index, _ -> index != i }
            if (isSafe(modifiedLevels)) {
                return true
            }
        }

        return false
    }
}