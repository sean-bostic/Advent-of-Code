package console

import days.Day
import days.Day01
import days.Day02
import days.Day03
import days.Day04
import days.Day05
import days.Day06
import days.Day07
import days.Day08

fun main() {
    println("🎄 Advent of Code 2024 🎄")

    while (true) {
        print("Enter day number (1-25) or 'Q' to quit: ")
        val input = readLine()?.trim()

        when {
            input == "q" || input == "quit" -> {
                println("Happy holidays! 🎅")
                break
            }
            input?.toIntOrNull() in 1..25 -> {
                val day = input!!.toInt()
                runDay(day)
            }
            else -> println("Please enter a valid day number (1-25) or 'Q' to quit")
        }
        println()
    }
}

fun runDay(dayNumber: Int) {
    val day = createDay(dayNumber)
    if (day == null) {
        println("Day $dayNumber not implemented yet!")
        return
    }

    println("=== days.Day $dayNumber ===")

    try {
        val input = day.loadInput()

        println("Running Part 1...")
        val part1Result = day.part1(input)
        println("Part 1 Result: $part1Result")

        println("Running Part 2...")
        val part2Result = day.part2(input)
        println("Part 2 Result: $part2Result")

    } catch (e: Exception) {
        println("Error running day $dayNumber: ${e.message}")
    }
}
fun createDay(dayNumber: Int): Day? {
    return when (dayNumber) {
        1 -> Day01()
        2 -> Day02()
        3 -> Day03()
        4 -> Day04()
        5 -> Day05()
        6 -> Day06()
        7 -> Day07()
        8 -> Day08()
        else -> null
    }
}