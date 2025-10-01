import days.Day03
import kotlin.test.Test

class Day03Test {
    private val day = Day03()

    @Test
    fun `Day 3 Part 1`() {
        val testInput = day.loadTestInput()
        val result = day.part1(testInput)
        assert(result == 161)
    }
}