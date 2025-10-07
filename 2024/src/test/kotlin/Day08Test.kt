import days.Day08
import kotlin.test.Test
import kotlin.test.assertEquals

class Day08Test {
    private val day = Day08()

    @Test
    fun `Day 8 Part 1`() {
        val testInput = day.loadTestInput()
        val result = day.part1(testInput)
        assert(result == 14)
    }

    @Test
    fun `Day 8 Part 2`() {
        val testInput = day.loadTestInput()
        val result = day.part2(testInput)
        assertEquals(34, result)
    }
}