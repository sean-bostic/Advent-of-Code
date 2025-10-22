package aoc_2024

import aoc_2024.days.Day03
import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {
    private val day = Day03()

    @Test
    fun `Day 3 Part 1`() {
        val testInput = day.loadTestInput(2024)
        val result = day.part1(testInput)
        assert(result == 161)
    }

    @Test
    fun `Day 3 Part 2`() {
        // different test input so hardcode the test input as a string
        val testInput =
            listOf(
                """
                xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+
                mul(32,64](mul(11,8)undo()?mul(8,5))
                """.trimIndent(),
            )

        val result = day.part2(testInput)
        assertEquals(48, result)
    }
}
