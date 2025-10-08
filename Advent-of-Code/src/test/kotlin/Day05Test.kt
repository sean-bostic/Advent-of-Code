import days.Day05
import org.junit.jupiter.api.Test

class Day05Test {
    private val day = Day05()

    @Test
    fun `Day 6 Part 1`() {
        val testInput = day.loadTestInput()
        val result = day.part1(testInput)
        assert(result == 143)
    }

    @Test
    fun `Day 6 Part 2`() {
        val testInput = day.loadTestInput()
        val result = day.part2(testInput)
        assert(result == 123)
    }
}