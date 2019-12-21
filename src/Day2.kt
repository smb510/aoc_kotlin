import base.Problem

class Day2 : Problem("day2.txt") {

    override fun part1() {
        val maps = getLines().map { line ->
            val counts = mutableMapOf<Char, Int>()
            line.map { letter ->
                val inc = counts.getOrDefault(letter, 0) + 1
                counts[letter] = inc
            }
            counts
        }

        val twoCount = maps.filter { it ->
            it.containsValue(2)
        }.size
        val threeCount = maps.filter { it -> it.containsValue(3) }.size
        println(twoCount * threeCount)
    }

    private fun diff(s1: String, s2: String): Int {
        return s1.zip(s2).filter { it.first != it.second }.size
    }

    override fun part2() {
        val lines = getLines()
        lines.map { line1 ->
            lines.map { line2 -> Pair(line1, line2) }
                    .filter { pair -> diff(pair.first, pair.second) == 1 }
        }.filter { it.isNotEmpty() }.forEach { println(it) }
    }
}


fun main(args: Array<String>) {
    val day2 = Day2()
    day2.part1()
    day2.part2()
}