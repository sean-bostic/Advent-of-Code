package aoc_2024.days

import core.Day

class Day19 : Day(19) {
    override fun part1(input: List<String>): Any {
        val (patterns, designs) = parseInput(input)

        return designs.count { design ->
            canFormDesign(design, patterns)
        }
    }

    override fun part2(input: List<String>): Any {
        val (patterns, designs) = parseInput(input)

//        println("Patterns: $patterns")
//        println("Designs count: ${designs.size}")

        val results =
            designs.map { design ->
                val count = countWaysToFormDesign(design, patterns)
                println("Design '$design': $count ways")
                count
            }

        return results.sum()
    }

    private fun parseInput(input: List<String>): Pair<List<String>, List<String>> {
        val patterns = input[0].split(", ").map { it.trim() }
        val designs = input.drop(2)

        return patterns to designs
    }

    private fun canFormDesign(
        design: String,
        patterns: List<String>,
    ): Boolean {
        val memo = mutableMapOf<String, Boolean>()

        fun canForm(remaining: String): Boolean {
            if (remaining.isEmpty()) return true

            if (remaining in memo) return memo[remaining]!!

            for (pattern in patterns) {
                if (remaining.startsWith(pattern)) {
                    val suffix = remaining.substring(pattern.length)
                    if (canForm(suffix)) {
                        memo[remaining] = true
                        return true
                    }
                }
            }

            memo[remaining] = false
            return false
        }

        return canForm(design)
    }

    private fun countWaysToFormDesign(
        design: String,
        patterns: List<String>,
    ): Long {
        val memo = mutableMapOf<String, Long>()

        fun countWays(remaining: String): Long {
            if (remaining.isEmpty()) return 1L

            if (remaining in memo) return memo[remaining]!!

            var totalWays = 0L

            for (pattern in patterns) {
                if (remaining.startsWith(pattern)) {
                    val suffix = remaining.substring(pattern.length)
                    totalWays += countWays(suffix)
                }
            }

            memo[remaining] = totalWays
            return totalWays
        }

        return countWays(design)
    }
}
