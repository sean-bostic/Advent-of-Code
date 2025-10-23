package aoc_2024.days

import core.Day

class Day24 : Day(24) {
    override fun part1(input: List<String>): Any {
        val (initialValues, gates) = parseInput(input)
        val wireValues = simulateCircuit(initialValues, gates)
        return calculateOutput(wireValues)
    }

    override fun part2(input: List<String>): Any {
        val (_, gates) = parseInput(input)

        val swappedWires = findSwappedWires(gates)

        return swappedWires.sorted().joinToString(",")
    }

    private fun findSwappedWires(gates: List<Gate>): Set<String> {
        val gateByOutput = gates.associateBy { it.output }

        val swapped = mutableSetOf<String>()

        val zWires = gates.map { it.output }
            .filter { it.startsWith("z") }
            .sortedBy { it.substring(1).toInt() }

        for (zWire in zWires) {
            if (zWire == zWires.last()) continue

            val gate = gateByOutput[zWire] ?: continue

            if (gate.operation != "XOR") {
                swapped.add(zWire)
            }
        }

        for (gate in gates) {
            if (gate.operation == "XOR") {
                val isFirstLevel = (gate.input1.startsWith("x") || gate.input1.startsWith("y")) &&
                        (gate.input2.startsWith("x") || gate.input2.startsWith("y"))

                if (!isFirstLevel && !gate.output.startsWith("z")) {
                    swapped.add(gate.output)
                }
            }
        }

        for (gate in gates) {
            if (gate.operation == "AND") {
                val isFirstBit = (gate.input1 == "x00" && gate.input2 == "y00") ||
                        (gate.input1 == "y00" && gate.input2 == "x00")

                if (!isFirstBit) {
                    val consumers = gates.filter {
                        it.input1 == gate.output || it.input2 == gate.output
                    }

                    if (consumers.any { it.operation != "OR" }) {
                        swapped.add(gate.output)
                    }
                }
            }
        }

        for (gate in gates) {
            if (gate.operation == "XOR") {
                val isFirstLevel = (gate.input1.startsWith("x") || gate.input1.startsWith("y")) &&
                        (gate.input2.startsWith("x") || gate.input2.startsWith("y"))
                val isFirstBit = (gate.input1 == "x00" || gate.input1 == "y00") &&
                        (gate.input2 == "x00" || gate.input2 == "y00")

                if (isFirstLevel && !isFirstBit) {
                    val consumers = gates.filter {
                        it.input1 == gate.output || it.input2 == gate.output
                    }

                    if (consumers.none { it.operation == "XOR" }) {
                        swapped.add(gate.output)
                    }
                }
            }
        }

        return swapped
    }

    private data class Gate(
        val input1: String,
        val input2: String,
        val operation: String,
        val output: String
    )

    private fun parseInput(input: List<String>): Pair<Map<String, Int>, List<Gate>> {
        val separatorIndex = input.indexOfFirst { it.isEmpty() }

        val initialValues = input.subList(0, separatorIndex)
            .associate { line ->
                val (wire, value) = line.split(": ")
                wire to value.toInt()
            }

        val gates = input.subList(separatorIndex + 1, input.size)
            .map { line ->
                val parts = line.split(" ")
                Gate(
                    input1 = parts[0],
                    input2 = parts[2],
                    operation = parts[1],
                    output = parts[4]
                )
            }

        return initialValues to gates
    }

    private fun simulateCircuit(
        initialValues: Map<String, Int>,
        gates: List<Gate>
    ): Map<String, Int> {
        val values = initialValues.toMutableMap()
        val pendingGates = gates.toMutableList()

        while (pendingGates.isNotEmpty()) {
            val iterator = pendingGates.iterator()
            var madeProgress = false

            while (iterator.hasNext()) {
                val gate = iterator.next()

                val input1Value = values[gate.input1]
                val input2Value = values[gate.input2]

                if (input1Value != null && input2Value != null) {
                    val output = when (gate.operation) {
                        "AND" -> input1Value and input2Value
                        "OR" -> input1Value or input2Value
                        "XOR" -> input1Value xor input2Value
                        else -> throw IllegalArgumentException("Unknown operation: ${gate.operation}")
                    }

                    values[gate.output] = output
                    iterator.remove()
                    madeProgress = true
                }
            }

            if (!madeProgress && pendingGates.isNotEmpty()) {
                break
            }
        }

        return values
    }

    private fun calculateOutput(wireValues: Map<String, Int>): Long {
        val zWires = wireValues.keys
            .filter { it.startsWith("z") }
            .sortedBy { it.substring(1).toInt() }

        var result = 0L
        for ((index, wire) in zWires.withIndex()) {
            val bit = wireValues[wire] ?: 0
            result = result or (bit.toLong() shl index)
        }

        return result
    }
}