package aoc_2024

import aoc_2024.days.Day15
import kotlin.test.Test
import kotlin.test.assertEquals

class Day15Test {
    private val day = Day15()

    @Test
    fun `Day 15 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals(10092, result)
    }
    @Test
    fun `Day 14 Part 2`() {
        val testINput = day.loadTestInput(2024)
        val result = day.part2(testINput)
        assertEquals(9021, result)
    }
}