package days

class Day08 : Day(8) {
    override fun part1(input: List<String>): Any {
        val height = input.size
        val width = input[0].length
        val antennas = parseAntennas(input)
        val antinodes = mutableSetOf<Position>()

        antennas.forEach { (_, positions) ->
            for (i in positions.indices) {
                for (j in i + 1 until positions.size) {
                    val a = positions[i]
                    val b = positions[j]

                    val antinode1 = Position(2 * b.x - a.x, 2 * b.y - a.y)
                    val antinode2 = Position(2 * a.x - b.x, 2 * a.y - b.y)

                    if (antinode1.isInBounds(width, height)) {
                        antinodes.add(antinode1)
                    }
                    if (antinode2.isInBounds(width, height)) {
                        antinodes.add(antinode2)
                    }
                }
            }
        }

        return antinodes.size
    }

    override fun part2(input: List<String>): Any {
        val height = input.size
        val width = input[0].length
        val antennas = parseAntennas(input)
        val antinodes = mutableSetOf<Position>()

        antennas.forEach { (_, positions) ->
            if (positions.size < 2) return@forEach

            for (i in positions.indices) {
                for (j in i + 1 until positions.size) {
                    val a = positions[i]
                    val b = positions[j]

                    val linePoints = findLinePoints(a, b, width, height)
                    antinodes.addAll(linePoints)
                }
            }
        }

        return antinodes.size
    }

    private data class Position(val x: Int, val y: Int) {
        fun isInBounds(width: Int, height: Int): Boolean {
            return x in 0 until width && y in 0 until height
        }
    }

    private fun parseAntennas(input: List<String>): Map<Char, List<Position>> {
        val antennas = mutableMapOf<Char, MutableList<Position>>()

        for (y in input.indices) {
            for (x in input[y].indices) {
                val char = input[y][x]
                if (char != '.') {
                    antennas.getOrPut(char) { mutableListOf() }.add(Position(x, y))
                }
            }
        }

        return antennas
    }

    private fun findLinePoints(a: Position, b: Position, width: Int, height: Int): Set<Position> {
        val points = mutableSetOf<Position>()

        val dx = b.x - a.x
        val dy = b.y - a.y

        val gcd = gcd(kotlin.math.abs(dx), kotlin.math.abs(dy))
        val stepX = dx / gcd
        val stepY = dy / gcd

        var current = a
        while (current.isInBounds(width, height)) {
            points.add(current)
            current = Position(current.x - stepX, current.y - stepY)
        }

        current = Position(a.x + stepX, a.y + stepY)
        while (current.isInBounds(width, height)) {
            points.add(current)
            current = Position(current.x + stepX, current.y + stepY)
        }

        return points
    }

    private fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }
}