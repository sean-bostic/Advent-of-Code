package aoc_2024

import aoc_2024.days.Day21
import kotlin.test.Test
import kotlin.test.assertEquals

class Day21Test {
    private val day = Day21()

    @Test
    fun `Day 21 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals(126384L, result)
    }
}