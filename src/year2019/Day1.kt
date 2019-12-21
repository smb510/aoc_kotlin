package year2019

import base.Problem
import kotlin.math.max

class Day1: Problem("year2019/day1.txt") {

    override fun part1() {
        val masses = getLines().map { it.toInt() }
        val fuel  = masses.map { (it / 3) - 2 }
        println("SUM No metafuel: ${fuel.sum()}")
    }

    override fun part2() {
        val masses = getLines().map { it.toInt() }
        val fuel  = masses.map { fuelNeeded(it) - it}
        println("SUM Including metafuel: ${fuel.sum()}")
    }

    private fun fuelNeeded(module: Int): Int {
        if (module <= 0) {
            return max(module, 0)
        }
        return module + fuelNeeded((module / 3) - 2)
    }
}

fun main(args: Array<String>) {
    Day1().part1()
    Day1().part2()
}

