package aoc_2024

import aoc_2024.days.Day08
import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Test {
    private val day = Day08()

    @Test
    fun `Day 8 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assert(result == 14)
    }

    @Test
    fun `Day 8 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)
        assertEquals(34, result)
    }
}