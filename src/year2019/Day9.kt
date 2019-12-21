package year2019

import base.Problem

class Day9 : Problem("year2019/day9.txt") {

    override fun part1() {
        val computer = IntcodeComputer(getLines()[0], 1L)
        val ret = computer.execute()
        println(ret)
    }
}

fun main() {
    val day9 = Day9()
    day9.part1()
}