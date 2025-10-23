package aoc_2024

import aoc_2024.days.Day25
import kotlin.test.Test
import kotlin.test.assertEquals

class Day25Test {
    private val day = Day25()

    @Test
    fun `Day 25 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)

        assertEquals(3, result)
    }
}