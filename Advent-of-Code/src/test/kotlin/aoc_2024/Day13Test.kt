package aoc_2024

import aoc_2024.days.Day13
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13Test {
    private val day = Day13()

    @Test
    fun `Day 13 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals(480, result)
    }
}
