package core

abstract class Day(val dayNumber: Int) {
    abstract fun part1(input: List<String>): Any

    abstract fun part2(input: List<String>): Any

    fun loadInput(year: Int): List<String> {
        return loadResourceLines("aoc_$year/day${dayNumber.toString().padStart(2, '0')}.txt")
    }

    fun loadTestInput(year: Int): List<String> {
        return loadResourceLines("aoc_$year/day${dayNumber.toString().padStart(2, '0')}.txt")
    }

    private fun loadResourceLines(path: String): List<String> {
        return this::class.java.classLoader
            .getResourceAsStream(path)
            ?.bufferedReader()
            ?.readLines()
            ?: emptyList()
    }
}
