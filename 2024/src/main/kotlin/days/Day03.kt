package days

class Day03 : Day(3){
    override fun part1(input: List<String>): Int {
        return input.joinToString("")
            .indices
            .asSequence()
            .mapNotNull { it.parseMulAt(input.joinToString("")) }
            .sum()
    }

    override fun part2(input: List<String>): Any {
        return 0 // finish in the top of the morning
    }
}

private fun Int.parseMulAt(text: String): Int? =
    text.drop(this)
        .takeIf { it.startsWith("mul(") }
        ?.drop(4)
        ?.takeWhile { it != ')' }
        ?.split(',')
        ?.takeIf { it.size == 2 }
        ?.let { (first, second) ->
            first.toIntOrNull()?.times(second.toIntOrNull() ?: return null)
        }
