package days

class Day03 : Day(3){
    override fun part1(input: List<String>): Int {
        return input.joinToString("")
            .indices
            .asSequence()
            .mapNotNull { it.parseMulAt(input.joinToString("")) }
            .sum()
    }

    override fun part2(input: List<String>): Int =
        input.joinToString("")
            .findEnabledMulInstructions()
            .sum()
}

private fun String.findAllMulInstructions(): Sequence<Int> =
    indices.asSequence()
        .mapNotNull { parseMulAt(it) }

private fun String.findEnabledMulInstructions(): Sequence<Int> = sequence {
    var enabled = true

    for (i in indices) {
        when {
            startsWith("do()", i) -> enabled = true
            startsWith("don't()", i) -> enabled = false
            enabled -> parseMulAt(i)?.let { yield(it) }
        }
    }
}

private fun String.parseMulAt(index: Int): Int? =
    drop(index)
        .takeIf { it.startsWith("mul(") }
        ?.drop(4)
        ?.takeWhile { it != ')' }
        ?.split(',')
        ?.takeIf { it.size == 2 }
        ?.let { (first, second) ->
            first.toIntOrNull()?.times(second.toIntOrNull() ?: return null)
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
