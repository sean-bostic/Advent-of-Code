package aoc_2024.days

import core.Day
import kotlin.math.pow

class Day17 : Day(17) {
    override fun part1(input: List<String>): Any {
        val (registers, program) = parseInput(input)
        val computer = Computer(registers, program)
        computer.run()
        return computer.output.joinToString(",")
    }

    override fun part2(input: List<String>): Any {
        val (_, program) = parseInput(input)
        return findQuineValue(program)
    }

    private fun findQuineValue(program: List<Int>): Long {
        var candidates = setOf(0L)

        for (targetIndex in program.indices.reversed()) {
            val nextCandidates = mutableSetOf<Long>()

            for (prevA in candidates) {
                for (bits in 0L..7L) {
                    val testA = (prevA shl 3) or bits

                    if (testA == 0L && targetIndex > 0) continue

                    val registers = Registers(testA, 0, 0)
                    val computer = Computer(registers, program)
                    computer.run()

                    val expected = program.subList(targetIndex, program.size)
                    if (computer.output == expected) {
                        nextCandidates.add(testA)
                    }
                }
            }

            candidates = nextCandidates
            if (candidates.isEmpty()) return 0L
        }

        return candidates.minOrNull() ?: 0L
    }

    private data class Registers(
        var a: Long,
        var b: Long,
        var c: Long,
    )

    private class Computer(
        private val registers: Registers,
        private val program: List<Int>,
    ) {
        private var ip = 0
        val output = mutableListOf<Int>()

        fun run() {
            while (ip < program.size) {
                val opcode = program[ip]
                val operand = if (ip + 1 < program.size) program[ip + 1] else 0

                executeInstruction(opcode, operand)
            }
        }

        private fun executeInstruction(
            opcode: Int,
            operand: Int,
        ) {
            when (opcode) {
                0 -> adv(operand)
                1 -> bxl(operand)
                2 -> bst(operand)
                3 -> jnz(operand)
                4 -> bxc(operand)
                5 -> out(operand)
                6 -> bdv(operand)
                7 -> cdv(operand)
            }
        }

        private fun getComboValue(operand: Int): Long =
            when (operand) {
                in 0..3 -> operand.toLong()
                4 -> registers.a
                5 -> registers.b
                6 -> registers.c
                else -> throw IllegalArgumentException("Invalid combo operand: $operand")
            }

        private fun adv(operand: Int) {
            val divisor = 2.0.pow(getComboValue(operand).toDouble()).toLong()
            registers.a = registers.a / divisor
            ip += 2
        }

        private fun bxl(operand: Int) {
            registers.b = registers.b xor operand.toLong()
            ip += 2
        }

        private fun bst(operand: Int) {
            registers.b = getComboValue(operand) % 8
            ip += 2
        }

        private fun jnz(operand: Int) {
            if (registers.a != 0L) {
                ip = operand
            } else {
                ip += 2
            }
        }

        private fun bxc(operand: Int) {
            registers.b = registers.b xor registers.c
            ip += 2
        }

        private fun out(operand: Int) {
            output.add((getComboValue(operand) % 8).toInt())
            ip += 2
        }

        private fun bdv(operand: Int) {
            val divisor = 2.0.pow(getComboValue(operand).toDouble()).toLong()
            registers.b = registers.a / divisor
            ip += 2
        }

        private fun cdv(operand: Int) {
            val divisor = 2.0.pow(getComboValue(operand).toDouble()).toLong()
            registers.c = registers.a / divisor
            ip += 2
        }
    }

    private fun parseInput(input: List<String>): Pair<Registers, List<Int>> {
        val registerA = input[0].substringAfter(": ").toLong()
        val registerB = input[1].substringAfter(": ").toLong()
        val registerC = input[2].substringAfter(": ").toLong()

        val program =
            input[4].substringAfter(": ")
                .split(",")
                .map { it.toInt() }

        return Registers(registerA, registerB, registerC) to program
    }
}
