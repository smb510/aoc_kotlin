package year2019

import base.Problem

class Day8 : Problem("year2019/day8.txt") {
    override fun part1() {
        val line = getLines()[0].map { it.toString().toInt() }
        val layers = line.withIndex().groupBy { it.index / 150 }.map { it.value.map { it.value } }
        val fewestZeros = layers.sortedBy { it -> it.count { it == 0 } }[0]
        println(fewestZeros.count { it == 1 } * fewestZeros.count { it == 2 })
    }

    override fun part2() {
        val line = getLines()[0].map { it.toString().toInt() }
        val layers = line.withIndex().groupBy { it.index / 150 }.map { it.value.map { it.value } }
        val matrices = layers.map { it -> it.withIndex().groupBy { it.index / 25 }.map { it.value.map { it.value } } }.reversed()
        var output: List<List<Int>>? = null
        matrices.forEach { it ->
            output = if (output == null) {
                it
            } else {
                output!!.zip(it).map { transformRow(it.first, it.second) }
            }
        }
        output!!.forEach { it ->
            val line = it.map {
                when (it) {
                    1 -> " "
                    0 -> "\u2588"
                    else -> ""
                }
            }
            println(line.joinToString(""))
        }
    }

    fun transformRow(baseRow: List<Int>, overlayRow: List<Int>): List<Int> {
        return baseRow.zip(overlayRow).map { it -> apply(it.first, it.second) }
    }

    private fun apply(base: Int, overlay: Int): Int {
        return when (overlay) {
            2 -> base
            else -> overlay
        }
    }
}

fun main() {
    val day8 = Day8()
    day8.part2()
}