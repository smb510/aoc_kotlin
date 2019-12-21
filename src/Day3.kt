import base.Problem

class Day3 : Problem("day3.txt") {
    data class Claim(val id: Int, val start: Pair<Int, Int>, val length: Int, val width: Int)


    override fun part1() {
        val regex = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)")
        val claims = getLines().map { regex.matchEntire(it) }
                .map {
                    val groups = it?.groups!!
                    Claim(groups[1]?.value?.toInt() ?: 0,
                            Pair(groups[2]?.value?.toInt() ?: 0, groups[3]?.value?.toInt() ?: 0),
                            groups[4]?.value?.toInt() ?: 0,
                            groups[5]?.value?.toInt() ?: 0)
                }
        val counts = mutableMapOf<Pair<Int, Int>, Int>().withDefault { 0 }
        claims.forEach {

        }
    }
}

fun main(args: Array<String>) {
    val day3 = Day3()
    day3.part1()
    day3.part2()
}