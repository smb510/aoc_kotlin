package year2019

import base.Problem

class Day7 : Problem("year2019/day7.txt") {

    override fun part1() {
        val input = getLines()[0]
        var lastOutput = 0L
        val outputs = listOf(0, 1, 2, 3, 4).permutations().map { permutation ->
            lastOutput = 0L
            for (index in permutation) {
                lastOutput = IntcodeComputer(input).simulate(lastOutput)
            }
            Pair(lastOutput, permutation.joinToString(""))
        }
        println("max : ${outputs.sortedByDescending { it.first }[0]}")
    }

    override fun part2() {
        val input = getLines()[0]
        var lastOutput = 0L
        val outputs = listOf(5L, 6L, 7L, 8L, 9L).permutations().map { permutation ->
            println("Trying ${permutation.joinToString("")}")
            lastOutput = Amplifiers(input, permutation).setup()
            Pair(lastOutput, permutation.joinToString(""))
        }.sortedByDescending { it.first }[0]
        println(outputs)
    }


    class Amplifiers(input: String, phase: List<Long>) {

        private var a = IntcodeComputer(input, phase[0])
        private var b = IntcodeComputer(input, phase[1])
        private val c = IntcodeComputer(input, phase[2])
        private val d = IntcodeComputer(input, phase[3])
        private val e = IntcodeComputer(input, phase[4])

        private val computers = mutableListOf(a, b, c ,d, e)


        fun setup(): Long {
            var input = 0L
            var computer = computers.removeAt(0)
            while (!computer.terminated) {
                input = computer.simulate(input)
                println("got output $input")
                if (computer.terminated) {
                    println("terminated")
                    return input
                }
                computers.add(computer)
                computer = computers.removeAt(0)
            }
            return input
        }


    }


    private fun <T> List<T>.permutations(): Set<List<T>> = when {
        isEmpty() -> setOf()
        size == 1 -> setOf(listOf(get(0)))
        else -> {
            val element = get(0)
            drop(1).permutations()
                    .flatMap { sublist -> (0..sublist.size).map { i -> sublist.plusAt(i, element) } }
                    .toSet()
        }
    }

    private fun <T> List<T>.plusAt(index: Int, element: T): List<T> = when {
        index !in 0..size -> throw Error("Cannot put at index $index because size is $size")
        index == 0 -> listOf(element) + this
        index == size -> this + element
        else -> dropLast(size - index) + element + drop(index)
    }
}

fun main() {
    val day7 = Day7()
    day7.part2()
}