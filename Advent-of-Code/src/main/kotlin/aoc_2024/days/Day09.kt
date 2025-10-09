
package aoc_2024.days
import core.Day

class Day09 : Day(9) {
    override fun part1(input: List<String>): Any {
        val diskMap = input[0]
        val blocks = parseDiskMap(diskMap)
        val compacted = compactDisk(blocks)
        return calculateChecksum(compacted)
    }

    override fun part2(input: List<String>): Any {
        val diskMap = input[0]
        val blocks = parseDiskMap(diskMap)
        val compacted = compactDiskWholeFiles(blocks)
        return calculateChecksum(compacted)
    }

    private fun parseDiskMap(diskMap: String): MutableList<Int?> {
        val blocks = mutableListOf<Int?>()
        var fileId = 0
        var isFile = true

        for (char in diskMap) {
            val length = char.digitToInt()

            if (isFile) {
                repeat(length) {
                    blocks.add(fileId)
                }
                fileId++
            } else {
                repeat(length) {
                    blocks.add(null)
                }
            }

            isFile = !isFile
        }

        return blocks
    }

    private fun compactDisk(blocks: MutableList<Int?>): List<Int?> {
        val result = blocks.toMutableList()

        while (true) {
            val freeIndex = result.indexOfFirst { it == null }
            if (freeIndex == -1) break

            val fileIndex = result.indexOfLast { it != null }
            if (fileIndex == -1 || fileIndex < freeIndex) break

            result[freeIndex] = result[fileIndex]
            result[fileIndex] = null
        }

        return result
    }

    private fun compactDiskWholeFiles(blocks: MutableList<Int?>): List<Int?> {
        val result = blocks.toMutableList()

        // Find all files and their properties
        val files = mutableListOf<FileInfo>()
        var i = 0
        while (i < result.size) {
            val fileId = result[i]
            if (fileId != null) {
                val startPos = i
                var size = 0
                while (i < result.size && result[i] == fileId) {
                    size++
                    i++
                }
                files.add(FileInfo(fileId, startPos, size))
            } else {
                i++
            }
        }

        files.sortByDescending { it.id }

        for (file in files) {
            val currentStart = result.indexOfFirst { it == file.id }
            if (currentStart == -1) continue

            var freeStart = -1
            var freeSize = 0

            for (pos in 0 until currentStart) {
                if (result[pos] == null) {
                    if (freeStart == -1) {
                        freeStart = pos
                        freeSize = 1
                    } else {
                        freeSize++
                    }

                    if (freeSize >= file.size) {
                        for (j in currentStart until currentStart + file.size) {
                            result[j] = null
                        }
                        for (j in freeStart until freeStart + file.size) {
                            result[j] = file.id
                        }
                        break
                    }
                } else {
                    freeStart = -1
                    freeSize = 0
                }
            }
        }

        return result
    }

    private data class FileInfo(val id: Int, val startPos: Int, val size: Int)

    private fun calculateChecksum(blocks: List<Int?>): Long {
        var checksum = 0L

        for (position in blocks.indices) {
            val fileId = blocks[position]
            if (fileId != null) {
                checksum += position * fileId
            }
        }

        return checksum
    }
}
