package aoc_2024.days

import core.Day

class Day22 : Day(22) {
    override fun part1(input: List<String>): Any {
        return input.sumOf { line ->
            var secret = line.toLong()
            repeat(2000) {
                secret = nextSecret(secret)
            }
            secret
        }
    }

    override fun part2(input: List<String>): Any {
        val sequenceTotals = mutableMapOf<List<Int>, Long>()

        for (line in input) {
            var secret = line.toLong()
            val prices = mutableListOf<Int>()

            prices.add((secret % 10).toInt())

            repeat(2000) {
                secret = nextSecret(secret)
                prices.add((secret % 10).toInt())
            }

            val changes = mutableListOf<Int>()
            for (i in 1 until prices.size) {
                changes.add(prices[i] - prices[i - 1])
            }

            val seenSequences = mutableSetOf<List<Int>>()

            for (i in 0..changes.size - 4) {
                val sequence = changes.subList(i, i + 4)

                if (sequence !in seenSequences) {
                    seenSequences.add(sequence.toList())
                    val price = prices[i + 4]
                    sequenceTotals[sequence.toList()] =
                        sequenceTotals.getOrDefault(sequence.toList(), 0L) + price
                }
            }
        }

        return sequenceTotals.values.maxOrNull() ?: 0L
    }

    private fun nextSecret(secret: Long): Long {
        var result = secret

        result = prune(mix(result, result * 64))
        result = prune(mix(result, result / 32))
        result = prune(mix(result, result * 2048))

        return result
    }

    private fun mix(secret: Long, value: Long): Long {
        return secret xor value
    }

    private fun prune(secret: Long): Long {
        return secret % 16777216
    }
}