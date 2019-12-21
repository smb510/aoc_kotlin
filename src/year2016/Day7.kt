package year2016

import base.Problem

class Day7 : Problem("year2016/day7.txt") {

    override fun part2() {
        println(getLines().filter { supportsSsl(it) }.size)
    }

    fun supportsSsl(str: String): Boolean {
        val firstDelimiter = str.indexOf("[")
        val secondDelimiter = str.indexOf("]")
        val firstSupernet = str.substring(0, firstDelimiter)
        val secondSupernet = str.substring(secondDelimiter + 1)
        val hypernet = str.substring(firstDelimiter + 1, secondDelimiter)

        val aba = "(\\w)(^\\1)\\1".toRegex()

        val firstSupernetMatches = aba.findAll(firstSupernet)
        var hasMatch = firstSupernetMatches.filter {
            hypernet.contains(it.groupValues[1] + it.groupValues[0] + it.groupValues[1])
        }.any()

        val secondSupernetMatches = aba.findAll(secondSupernet)

        hasMatch = hasMatch || secondSupernetMatches.filter {
            hypernet.contains(it.groupValues[1] + it.groupValues[0] + it.groupValues[1])
        }.any()

        return hasMatch
    }

}

fun main() {
    val day7 = Day7()
    day7.part2()
}