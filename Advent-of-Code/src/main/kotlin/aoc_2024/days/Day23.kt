package aoc_2024.days

import core.Day

class Day23 : Day(23) {
    override fun part1(input: List<String>): Any {
        val connections = parseConnections(input)
        val triangles = findTriangles(connections)

        return triangles.count { triangle ->
            triangle.any { it.startsWith('t') }
        }
    }

    override fun part2(input: List<String>): Any {
        val connections = parseConnections(input)
        val largestClique = findLargestClique(connections)

        return largestClique.sorted().joinToString(",")
    }

    private fun parseConnections(input: List<String>): Map<String, Set<String>> {
        val connections = mutableMapOf<String, MutableSet<String>>()

        for (line in input) {
            val (a, b) = line.split("-")
            connections.getOrPut(a) { mutableSetOf() }.add(b)
            connections.getOrPut(b) { mutableSetOf() }.add(a)
        }

        return connections
    }

    private fun findTriangles(connections: Map<String, Set<String>>): Set<Set<String>> {
        val triangles = mutableSetOf<Set<String>>()
        val computers = connections.keys.toList()

        for (i in computers.indices) {
            for (j in i + 1 until computers.size) {
                for (k in j + 1 until computers.size) {
                    val a = computers[i]
                    val b = computers[j]
                    val c = computers[k]

                    if (connections[a]!!.contains(b) &&
                        connections[a]!!.contains(c) &&
                        connections[b]!!.contains(c)) {
                        triangles.add(setOf(a, b, c))
                    }
                }
            }
        }

        return triangles
    }

    private fun findLargestClique(connections: Map<String, Set<String>>): Set<String> {
        var maxClique = emptySet<String>()

        fun bronKerbosch(
            R: Set<String>,
            P: MutableSet<String>,
            X: MutableSet<String>
        ) {
            if (P.isEmpty() && X.isEmpty()) {
                if (R.size > maxClique.size) {
                    maxClique = R
                }
                return
            }

            val PCopy = P.toSet()
            for (v in PCopy) {
                val neighbors = connections[v] ?: emptySet()
                bronKerbosch(
                    R + v,
                    P.intersect(neighbors).toMutableSet(),
                    X.intersect(neighbors).toMutableSet()
                )
                P.remove(v)
                X.add(v)
            }
        }

        bronKerbosch(
            emptySet(),
            connections.keys.toMutableSet(),
            mutableSetOf()
        )

        return maxClique
    }
}