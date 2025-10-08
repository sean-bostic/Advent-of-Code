import days.Day06
import kotlin.test.Test

class Day06Test {
    private val day = Day06()

    @Test
    fun `Day 6 Part 1`() {
        val testInput = day.loadTestInput()
        val result = day.part1(testInput)
        assert(result == 41)
    }

    @Test
    fun `Day 6 Part 2`() {
        val testInput = day.loadTestInput()
        val result = day.part2(testInput)
        assert(result == 6)
    }

}