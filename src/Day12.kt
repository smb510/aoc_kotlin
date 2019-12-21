import base.Problem
import java.lang.IllegalArgumentException

class Day12 : Problem("day12.txt") {

    enum class State {
        PLANT, POT
    }

    val initial =
            "...................................................................................................." +
            "...................................................................................................." +
            "...................................................................................................." +
            "...................................................................................................." +
            "...................................................................................................." +
            "...................................................................................................." +
            "...................................................................................................." +
            "...................................................................................................." +
            "...................................................................................................." +
            "...................................................................................................." +
                    ".##..#.#..##..##..##...#####.#.....#..#..##.###.#.####......#.......#..###.#.#.##.#.#.###...##.###.#" +
                    "...................................................................................................." +
                    "...................................................................................................." +
                    "...................................................................................................." +
                    "...................................................................................................." +
                    "...................................................................................................." +
                    "...................................................................................................." +
                    "...................................................................................................." +
                    "...................................................................................................." +
                    "...................................................................................................." +
                    "...................................................................................................." +
                    "...................................................................................................." +
                    "...................................................................................................." +
                    "...................................................................................................."


    override fun part1() {
        var pots: MutableList<State> = mutableListOf()
        val transitions = mutableMapOf<List<State>, State>()
        val slices = getLines().map { it ->
            it.slice(0..4)
                    .map { letter ->
                        when (letter) {
                            '#' -> State.PLANT
                            '.' -> State.POT
                            else -> throw IllegalArgumentException("Can only be # or .")
                        }
                    }
        }
        val endStates = getLines().map { it ->
            it.last()
        }.map { letter ->
            when (letter) {
                '#' -> State.PLANT
                '.' -> State.POT
                else -> throw IllegalArgumentException("Can only be # or .")
            }
        }

        println(transitions)

        slices.zip(endStates).forEach { it ->
            run {
                transitions[it.first] = it.second
            }
        }


        initial.map {
            when (it) {
                '#' -> State.PLANT
                '.' -> State.POT
                else -> throw IllegalArgumentException("Can only be # or .")
            }
        }.forEach { pots.add(it) }

        println("0: ${printPots(pots.subList(90, 235))}")
        val zeroPotIndex = 1000
        var lastSum = pots.withIndex().filter { it.value == State.PLANT }.map { it.index - zeroPotIndex }.sum()
        for (i in 1..2000) {
            pots = transform(pots, transitions)
            val sum = pots.withIndex().filter { it.value == State.PLANT }.map { it.index - zeroPotIndex }.sum()
            println("$i: $sum --> ${sum - lastSum}")
            lastSum = sum
        }

        val sum = pots.withIndex().filter { it.value == State.PLANT }.map { it.index - zeroPotIndex }.sum()
        println("The sum is: $sum")
    }


    fun transform(pots: List<State>, transitions: Map<List<State>, State>): MutableList<State> {
        val newPots = mutableListOf<State>()
        for (i in 2..pots.size - 3) {
            val slice = pots.slice(i - 2..i + 2)
            newPots.add(transitions[slice] ?: State.POT)
        }
        newPots.add(State.POT)
        newPots.add(State.POT)
        newPots.add(0, State.POT)
        newPots.add(0, State.POT)
        return newPots
    }

    private fun printPots(pots: List<State>): String {
        return pots.joinToString("") {
            when (it) {
                State.POT -> "."
                State.PLANT -> "#"
            }
        }
    }
}

fun main(args: Array<String>) {
    val day12 = Day12()
    day12.part1()
    day12.part2()
}