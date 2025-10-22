package aoc_2024

import aoc_2024.days.Day11
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    private val day = Day11()

    @Test
    fun `Day 11 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals(55312L, result)
    }
}
