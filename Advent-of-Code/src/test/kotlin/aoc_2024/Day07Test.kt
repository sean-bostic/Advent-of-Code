package aoc_2024

import aoc_2024.days.Day07
import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test  {
    private val day = Day07()

    // Have to check against a Long literals for Day 7 tests.
    @Test
    fun `Day 7 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals( 3749L, actual = result)
    }

    @Test
    fun `Day 7 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)
        assertEquals( 11387L, actual = result)
    }
}