package aoc_2024

import aoc_2024.days.Day10
import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {
    private val day = Day10()

    @Test
    fun `Day 10 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals(36, result)
    }

    @Test
    fun `Day 10 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)
        assertEquals(81, result)
    }
}
