package year2019

import base.Problem

class Day4 : Problem("year2019/day4.txt") {
    override fun part1() {
        println((372037 .. 905157).filter { isAcceptable(it) }.count())
    }

    override fun part2() {
        println((372037 .. 905157).filter { isAcceptableWithMatchingPair(it) }.count())
    }

    private fun isAcceptableWithMatchingPair(number: Int) : Boolean {
        val numbers = "(\\d)\\1+".toRegex().findAll(number.toString())
        return (numbers.filter { it.value.length == 2 }).toList().isNotEmpty()
                && number.toString().toCharArray().contentEquals(number.toString().toCharArray().sortedArray())
    }

    private fun isAcceptable(number: Int): Boolean {
        if(!".*(\\d)\\1+.*".toRegex().containsMatchIn(number.toString())) {
            return false
        }
        return number.toString().toCharArray().contentEquals(number.toString().toCharArray().sortedArray())
    }

}

fun main() {
    val day4 = Day4()
    day4.part1()
    day4.part2()
}