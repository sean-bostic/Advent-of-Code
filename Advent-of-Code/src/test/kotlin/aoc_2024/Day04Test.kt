package aoc_2024

import aoc_2024.days.Day04
import org.junit.jupiter.api.Test

class Day04Test {
    private val day = Day04()

    @Test
    fun `Day 4 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assert(result == 18)
    }

    @Test
    fun `Day 4 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)
        assert(result == 9)
    }
}