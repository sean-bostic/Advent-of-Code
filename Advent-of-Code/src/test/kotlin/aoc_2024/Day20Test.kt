package aoc_2024

import aoc_2024.days.Day20
import kotlin.test.Test
import kotlin.test.assertEquals

class Day20Test {
    private val day = Day20()

    @Test
    fun `Day 20 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)

        assertEquals(44, result)
    }
}