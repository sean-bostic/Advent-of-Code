import days.Day02
import kotlin.test.Test
import kotlin.test.assertEquals

class Day02Test {
    private val day = Day02()

    @Test
    fun `Day 2 Part 1`() {
        val testInput = day.loadTestInput()
        val result = day.part1(testInput)
        assertEquals(2, result, "Part 1 should return 2 for test input")
    }

    @Test
    fun `Day 2 Part 2`() {
        val testInput = day.loadTestInput()
        val result = day.part2(testInput)
        assertEquals(4, result, "Using the Problem Dampener we should get [4]")
    }
}