package aoc_2024

import aoc_2024.days.Day24
import kotlin.test.Test
import kotlin.test.assertEquals

class Day24Test {
    private val day = Day24()

    @Test
    fun `Day 24 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)

        assertEquals(2024.toLong(), result)
    }

    @Test
    fun `Day 24 Part 2`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part2(testInput)

        assertEquals("ffh,hwm,kjc,mjb,rvg,tgd,wpb,z02,z03,z05,z06,z07,z08,z10,z11", result)
    }
}