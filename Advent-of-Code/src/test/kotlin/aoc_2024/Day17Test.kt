package aoc_2024

import aoc_2024.days.Day17
import kotlin.test.Test
import kotlin.test.assertEquals

class Day17Test {
    private val day = Day17()

    @Test
    fun `Day 17 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals("4,6,3,5,6,3,5,2,1,0", result)
    }
}
