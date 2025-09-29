package days

import java.io.File


abstract class Day(private val dayNumber: Int){
    abstract fun part1(input: List<String>): Any
    abstract fun part2(input: List<String>): Any

    fun loadInput(): List<String> {
        val paddedDay = dayNumber.toString().padStart(2, '0')
        val relativePath = "src/main/resources/day$paddedDay.txt"
        val file = File(relativePath)

        return file.readLines()
    }

    fun loadTestInput(): List<String> {
        val paddedDay = dayNumber.toString().padStart(2, '0')
        val relativePath = "src/test/resources/day$paddedDay.txt"
        val file = File(relativePath)
        return file.readLines()
    }
}