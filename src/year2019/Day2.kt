package year2019

import base.Problem

class Day2: Problem("year2019/day2.txt") {

    var index = 0
    private var items: MutableList<Int> = mutableListOf()
    override fun part1() {
        println(IntcodeComputer(getLines()[0]).simulate(2))
    }

    override fun part2() {
        val input = getLines()[0]
        for (noun in 0..99L) {
            for (verb in 0..99L) {
                var output = IntcodeComputer(input).simulate(verb)
                if (output == 19690720L) {
                    print("$noun $verb")
                    return
                }
            }
        }
        println("not found")
    }
}

fun main() {
    Day2().part1()
    Day2().part2()
}