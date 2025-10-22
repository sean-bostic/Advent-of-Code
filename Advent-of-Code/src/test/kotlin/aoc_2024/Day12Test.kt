package aoc_2024

import aoc_2024.days.Day12
import kotlin.test.Test
import kotlin.test.assertEquals

class Day12Test {
    private val day = Day12()

    @Test
    fun `Day 12 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals(1930, result)
    }

    @Test
    fun `Day 12 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)
        assertEquals(1206, result)
    }
}
