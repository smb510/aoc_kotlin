package year2019

import base.Problem

class Day5 : Problem("year2019/day5.txt") {

    override fun part1() {
        val computer = IntcodeComputer(getLines()[0]).simulate(0)
    }

    override fun part2() {
        super.part2()
    }
}

fun main() {
    val day5 = Day5()
    day5.part1()
    day5.part2()
}