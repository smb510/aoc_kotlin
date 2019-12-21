import base.Problem

class Day1: Problem("day1.txt") {
    override fun part1() {
        println(getLines().map { it -> it.toInt() }.fold(0) { a, b -> a + b})
    }

    override fun part2() {
        val ints = getLines().map {it.toInt()}
        val seen = mutableSetOf<Int>()
        var i = 0
        val sequence = generateSequence(0) {
            val sum = it + ints[i % ints.size]
            i++
            if (seen.contains(sum)) {
                print(sum)
                return@generateSequence null
            }
            seen.add(sum)
            sum
        }
        sequence.last()
    }
}

fun main(args: Array<String>) {
    val day1 = Day1()
    day1.part1()
    day1.part2()
}