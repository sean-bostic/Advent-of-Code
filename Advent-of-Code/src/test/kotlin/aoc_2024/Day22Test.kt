package aoc_2024

import aoc_2024.days.Day22
import kotlin.test.Test
import kotlin.test.assertEquals

class Day22Test {
    private val day = Day22()

    @Test
    fun `Day 22 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assertEquals(37327623L, result)
    }

    @Test
    fun `Day 22 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)
        assertEquals(24L, result)
    }
}