package console

import aoc_2024.Year2024
import core.YearRegistry

fun main() {
    YearRegistry.register(2024, Year2024.getAllDays())

    println("🎄 Advent of Code 🎄")
    println()

    val years = YearRegistry.getAllYears()
    println("Available years: ${years.map { it.year }.joinToString(", ")}")
    println()

    while (true) {
        print("Enter year (or 'Q' to quit): ")
        val yearInput = readLine()?.trim()

        when {
            yearInput == "q" || yearInput == "Q" -> {
                println("Happy holidays! 🎅")
                break
            }
            yearInput?.toIntOrNull() != null -> {
                val year = yearInput.toInt()
                val yearInfo = YearRegistry.getYearData(year)

                if (yearInfo == null) {
                    println("Year $year not found. Available years: ${years.map { it.year }.joinToString(", ")}")
                    println()
                    continue
                }

                runYear(year, yearInfo.days.map { it.dayNumber })
            }
            else -> println("Please enter a valid year or 'Q' to quit")
        }
        println()
    }
}

fun runYear(
    year: Int,
    availableDays: List<Int>,
) {
    println("\n=== Year $year ===")
    println("Available days: ${availableDays.joinToString(", ")}")

    while (true) {
        print("\nEnter day number (1-25), 'ALL' to run all days, or 'B' to go back: ")
        val input = readLine()?.trim()

        when {
            input == "b" || input == "B" -> {
                break
            }
            input == "all" || input == "ALL" -> {
                availableDays.forEach { day ->
                    runDay(year, day)
                    println()
                }
            }
            input?.toIntOrNull() in 1..25 -> {
                val day = input!!.toInt()
                if (day in availableDays) {
                    runDay(year, day)
                } else {
                    println("Day $day not implemented yet for year $year!")
                }
            }
            else -> println("Please enter a valid day number (1-25), 'ALL', or 'B' to go back")
        }
    }
}

fun runDay(
    year: Int,
    dayNumber: Int,
) {
    val day = YearRegistry.getDay(year, dayNumber)

    if (day == null) {
        println("Day $dayNumber not found for year $year!")
        return
    }

    println("\n=== Year $year - Day $dayNumber ===")

    try {
        val input = day.loadInput(year)

        if (input.isEmpty()) {
            println("⚠️ No input file found!")
            println("Expected: resources/aoc_$year/day${dayNumber.toString().padStart(2, '0')}.txt")
            return
        }

        print("Running Part 1... ")
        val part1Start = System.currentTimeMillis()
        val part1Result = day.part1(input)
        val part1Time = System.currentTimeMillis() - part1Start
        println("✓")
        println("Part 1: $part1Result (${part1Time}ms)")

        print("Running Part 2... ")
        val part2Start = System.currentTimeMillis()
        val part2Result = day.part2(input)
        val part2Time = System.currentTimeMillis() - part2Start
        println("✓")
        println("Part 2: $part2Result (${part2Time}ms)")

        println("Total time: ${part1Time + part2Time}ms")
    } catch (e: Exception) {
        println("\n❌ Error running day $dayNumber: ${e.message}")
        e.printStackTrace()
    }
}
