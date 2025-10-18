package aoc_2024

import aoc_2024.days.Day18
import kotlin.test.Test
import kotlin.test.assertEquals

class Day18Test {
    private val day = Day18()

    @Test
    fun `Day 18 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals(22, result)
    }

    @Test
    fun `Day 18 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)
        assertEquals("6,1", result)
    }
}