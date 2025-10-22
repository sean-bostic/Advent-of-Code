package aoc_2024.days

import core.Day
import kotlin.math.abs

class Day01 : Day(1) {
    override fun part1(input: List<String>): Int {
        // Create two seperate standalone lists that can be sorted to make easy comparison of numbers
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        input.forEach { line ->
            val numbers = line.trim().split("\\s+".toRegex())
            leftList.add(numbers[0].toInt())
            rightList.add(numbers[1].toInt())
        }

        leftList.sort()
        rightList.sort()

        // Get absolute differences for each pair of numbers in both lists and then sum those to one variable
        var totalDistance = 0
        for (i in leftList.indices) {
            val distance = abs(leftList[i] - rightList[i])
            totalDistance += distance
        }

        return totalDistance
    }

    override fun part2(input: List<String>): Any {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        input.forEach { line ->
            val numbers = line.trim().split("\\s+".toRegex())
            leftList.add(numbers[0].toInt())
            rightList.add(numbers[1].toInt())
        }

        var similarityScore = 0

        leftList.forEach { leftNumber ->
            val countInOtherList = rightList.count { it == leftNumber }
            similarityScore += leftNumber * countInOtherList
        }
        return similarityScore
    }
}
