package aoc_2025

import aoc_2025.days.Day01
import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {
    private val day = Day01()

    @Test
    fun `Test Day1 Part 1`() {
        val testInput = day.loadTestInput(2025)
        val result = day.part1(testInput)
        println(result)
        assertEquals(3, result)
    }

    @Test
    fun `Test Day1 Part 2`() {
        val testInput = day.loadTestInput(2025)
        val result = day.part2(testInput)
        println(result)
        assertEquals(6, result)
    }
}