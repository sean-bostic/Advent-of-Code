package aoc_2024

import aoc_2024.days.Day19
import kotlin.test.Test
import kotlin.test.assertEquals

class Day19Test {
    private val day = Day19()

    @Test
    fun `Day 19 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)

        assertEquals(6, result)
    }

    @Test
    fun `Day 19 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)

        assertEquals(16L, result)
    }
}