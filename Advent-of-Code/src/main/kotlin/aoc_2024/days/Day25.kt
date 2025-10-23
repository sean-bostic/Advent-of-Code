package aoc_2024.days

import core.Day

class Day25 : Day(25) {
    override fun part1(input: List<String>): Any {
        val schematics = parseSchematics(input)
        val locks = schematics.filter { it.isLock }.map { it.heights }
        val keys = schematics.filter { !it.isLock }.map { it.heights }

        var fittingPairs = 0

        for (lock in locks) {
            for (key in keys) {
                if (fits(lock, key)) {
                    fittingPairs++
                }
            }
        }

        return fittingPairs
    }

    override fun part2(input: List<String>): Any {
        return "Merry Christmas! 🎄"
    }

    private data class Schematic(
        val isLock: Boolean,
        val heights: List<Int>
    )

    private fun parseSchematics(input: List<String>): List<Schematic> {
        val schematics = mutableListOf<Schematic>()
        var i = 0

        while (i < input.size) {
            if (input[i].isEmpty()) {
                i++
                continue
            }

            val schematic = input.subList(i, minOf(i + 7, input.size))

            if (schematic.size == 7) {
                val isLock = schematic[0].all { it == '#' }
                val heights = calculateHeights(schematic, isLock)
                schematics.add(Schematic(isLock, heights))
            }

            i += 7
        }

        return schematics
    }

    private fun calculateHeights(schematic: List<String>, isLock: Boolean): List<Int> {
        val width = schematic[0].length
        val heights = MutableList(width) { 0 }

        if (isLock) {
            for (col in 0 until width) {
                for (row in 1 until schematic.size) {
                    if (schematic[row][col] == '#') {
                        heights[col]++
                    } else {
                        break
                    }
                }
            }
        } else {
            for (col in 0 until width) {
                for (row in schematic.size - 2 downTo 0) {
                    if (schematic[row][col] == '#') {
                        heights[col]++
                    } else {
                        break
                    }
                }
            }
        }

        return heights
    }

    private fun fits(lock: List<Int>, key: List<Int>): Boolean {
        val availableSpace = 5

        for (i in lock.indices) {
            if (lock[i] + key[i] > availableSpace) {
                return false
            }
        }

        return true
    }
}
