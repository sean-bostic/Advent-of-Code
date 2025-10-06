import days.Day07
import kotlin.test.Test
import kotlin.test.assertEquals

class Day07Test  {
    private val day = Day07()

    @Test
    fun `Day 7 Part 1`() {
        val testInput = day.loadTestInput()
        val result = day.part1(testInput)
        assertEquals( 3749L, actual = result) // Have to check against a Long literal.
    }
}