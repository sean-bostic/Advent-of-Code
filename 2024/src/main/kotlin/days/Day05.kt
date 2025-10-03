package days

class Day05 : Day(5) {
    override fun part1(input: List<String>): Any {
        val (rules, updates) = parseInput(input)

        return updates
            .filter { isValidUpdate(it, rules) }
            .sumOf { it[it.size / 2] }
    }

    override fun part2(input: List<String>): Any {
        val (rules, updates) = parseInput(input)

        return updates
            .filterNot { isValidUpdate(it, rules) }
            .map { fixUpdate(it, rules) }
            .sumOf { it[it.size / 2] }
    }

    private fun parseInput(input: List<String>): Pair<Set<Pair<Int, Int>>, List<List<Int>>> {
        val splitIndex = input.indexOf("")

        val rules = input.subList(0, splitIndex)
            .map { line ->
                val (x, y) = line.split("|").map { it.toInt() }
                x to y
            }
            .toSet()

        val updates = input.subList(splitIndex + 1, input.size)
            .map { line -> line.split(",").map { it.toInt() } }

        return rules to updates
    }

    private fun isValidUpdate(update: List<Int>, rules: Set<Pair<Int, Int>>): Boolean {
        for (i in update.indices) {
            for (j in i + 1 until update.size) {
                if (update[j] to update[i] in rules) {
                    return false
                }
            }
        }
        return true
    }
    private fun fixUpdate(update: List<Int>, rules: Set<Pair<Int, Int>>): List<Int> {
        return update.sortedWith { a, b ->
            when {
                a to b in rules -> -1
                b to a in rules -> 1
                else -> 0
            }
        }
    }
}