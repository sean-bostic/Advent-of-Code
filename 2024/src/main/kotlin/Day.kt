import java.io.File

abstract class Day(private val dayNumber: Int){
    abstract fun part1(input: List<String>): Any
    abstract fun part2(input: List<String>): Any

    fun loadInput(): List<String> {
        val paddedDay = dayNumber.toString().padStart(2, '0')
        val fileName = "src/main/resources/day$paddedDay.txt"

        return try {
            File(fileName).readLines()
        } catch (e: Exception) {
            println("Error reading input file: $fileName")
            emptyList()
        }
    }

    fun loadTestInput(): List<String> {
        val paddedDay = dayNumber.toString().padStart(2, '0')
        val fileName = "src/test/resources/day$paddedDay.txt"

        return try {
            File(fileName).readLines()
        } catch (e: Exception) {
            println("Error reading input file: $fileName")
            emptyList()
        }
    }
}