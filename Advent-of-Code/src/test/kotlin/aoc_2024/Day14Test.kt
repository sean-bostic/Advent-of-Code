package aoc_2024

import aoc_2024.days.Day14
import kotlin.test.Test
import kotlin.test.assertEquals

class Day14Test {
    private val day = Day14()

    @Test
    fun `Day 14 Part 1`() {
        val testINput = day.loadTestInput(2024)
        val result = day.part1(testINput)
        assertEquals(12, result)
    }
}
