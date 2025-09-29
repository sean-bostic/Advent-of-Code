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

    println("=== Day $dayNumber ===")

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
        else -> null
    }
}