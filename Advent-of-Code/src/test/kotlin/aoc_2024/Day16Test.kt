package aoc_2024

import aoc_2024.days.Day16
import kotlin.test.Test
import kotlin.test.assertEquals

class Day16Test {
    private val day = Day16()

    @Test
    fun `Day 16 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals(result, 7036)
    }

    @Test
    fun `Day 16 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)
        assertEquals(result, 45)
    }
}
