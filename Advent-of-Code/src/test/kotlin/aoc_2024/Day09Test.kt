package aoc_2024

import aoc_2024.days.Day09
import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {
    private val day = Day09()

    @Test
    fun `Day 9 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals(1928L, result)
    }

    @Test
    fun `Day 9 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)
        assertEquals(2858L, result)
    }
}
