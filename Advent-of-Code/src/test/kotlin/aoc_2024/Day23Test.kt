package aoc_2024

import aoc_2024.days.Day23
import kotlin.test.Test
import kotlin.test.assertEquals

class Day23Test {
    private val day = Day23()

    @Test
    fun `Day 23 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals(7, result)
    }

    @Test
    fun `Day 23 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)
        assertEquals("co,de,ka,ta", result)
    }
}