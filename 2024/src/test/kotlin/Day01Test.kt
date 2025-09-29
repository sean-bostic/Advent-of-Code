import kotlin.test.Test
import kotlin.test.assertEquals

class Day01Test {
    private val day = Day01()

    @Test
    fun `Day 1 Part 1`() {
        val testInput = day.loadTestInput()
        val result = day.part1(testInput)
        println(result)
        assertEquals(11, result, "Part 1 should return 11 for test input")
    }
    @Test
    fun `Day 1 Part 2`() {
        val testInput = day.loadTestInput()
        val result = day.part2(testInput)
        println(result)
        assertEquals(31, result, "Part 2 should return 31 for test input")
    }
}