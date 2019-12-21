import base.Problem

class Day24_2017 : Problem("day24_2017.txt") {

    val parts = mutableMapOf<Int, MutableList<Int>>()

    override fun part1() {
        val lines = getLines()

        lines.forEach { line ->
            run {
                val components = line.split("/")
                val first = components[0].trim().toInt()
                val second = components[1].trim().toInt()
                parts.getOrPut(first, { mutableListOf() }).add(second)
                parts.getOrPut(second, { mutableListOf() }).add(first)
            }
        }
        print(getChildSums(0, parts))
    }

    private fun getChildSums(currentValue: Int, parts: MutableMap<Int, MutableList<Int>>): Pair<Int, Int> {
        val available = parts.getOrDefault(currentValue, mutableListOf()).toMutableList()
        if (available.isEmpty()) {
            return Pair(currentValue, currentValue)
        }
        val summed = available.map {
            val copyMap = parts.toMutableMap()
            copyMap[it] = (copyMap[it]?: mutableListOf()).minus(currentValue).toMutableList()
            copyMap[currentValue] = (copyMap[currentValue]?: mutableListOf()).minus(it).toMutableList()
            return@map getChildSums(it, copyMap)
        }.sortedByDescending { it.second }
        return Pair(summed[0].first, summed[0].second + 2 * currentValue)

    }

    private fun getChildSumsLongest(currentValue: Int, parts: MutableMap<Int, MutableList<Int>>): Triple<Int, Int, Int> {
        val available = parts.getOrDefault(currentValue, mutableListOf()).toMutableList()
        if (available.isEmpty()) {
            return Triple(currentValue, currentValue, 1)
        }
        val summed = available.map {
            val copyMap = parts.toMutableMap()
            copyMap[it] = (copyMap[it]?: mutableListOf()).minus(currentValue).toMutableList()
            copyMap[currentValue] = (copyMap[currentValue]?: mutableListOf()).minus(it).toMutableList()
            return@map getChildSumsLongest(it, copyMap)
        }.sortedWith(compareBy({-1 * it.third}, {-1* it.second}))
        println(summed)
        println(summed[0])

        return Triple(summed[0].first, summed[0].second + 2 * currentValue, summed[0].third + 1)
    }

    override fun part2() {
        val lines = getLines()

        lines.forEach { line ->
            run {
                val components = line.split("/")
                val first = components[0].trim().toInt()
                val second = components[1].trim().toInt()
                parts.getOrPut(first, { mutableListOf() }).add(second)
                parts.getOrPut(second, { mutableListOf() }).add(first)
            }
        }
        print(getChildSumsLongest(0, parts))
    }
}

fun main(args: Array<String>) {
    Day24_2017().part2()
}