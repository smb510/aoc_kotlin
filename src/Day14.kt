import base.Problem

class Day14 : Problem("day14.txt") {

    override fun part1() {
        val recipes = mutableListOf<Int>()
        var elf1Index = 0
        var elf2Index = 1
        recipes.add(3)
        recipes.add(7)
        while (recipes.size <= 760231) {
            val indices = elfYourshelf(elf1Index, elf2Index, recipes)
            elf1Index = indices.first
            elf2Index = indices.second
        }
        println(recipes.subList(760221, 760231).joinToString(""))
    }

    override fun part2() {
        val recipes = mutableListOf<Int>()
        var elf1Index = 0
        var elf2Index = 1
        recipes.add(3)
        recipes.add(7)
        var i = 0
        val lastVals = listOf(7,6,0,2,2,1)

        while (lastVals.joinToString("") !in recipes.takeLast(10).joinToString("")) {
            if (i% 100 == 0) { println("$i") }
            val elf1Val = recipes[elf1Index]
            val elf2Val = recipes[elf2Index]
            val sum = elf1Val + elf2Val
            val digits: List<Int> = sum.toString().map { Character.getNumericValue(it) }
            recipes += digits
            elf1Index = (elf1Index + elf1Val + 1) % recipes.size
            elf2Index = (elf2Index + elf2Val + 1) % recipes.size
            i++
        }
        val index = recipes.joinToString("").indexOf(lastVals.joinToString (""))
        println("${lastVals.joinToString("")} appears after $index characters")
    }


    fun elfYourshelf(elf1Idx: Int, elf2Idx: Int, recipes: MutableList<Int>): Pair<Int, Int> {
        val elf1Val = recipes[elf1Idx]
        val elf2Val = recipes[elf2Idx]
        val sum = elf1Val + elf2Val
        val digits: List<Int> = sum.toString().map {Character.getNumericValue(it) }
        recipes.addAll(digits)
        return Pair((elf1Idx + elf1Val + 1) % recipes.size, (elf2Idx + elf2Val + 1) % recipes.size)
    }


}

fun main(args: Array<String>) {
    val day14 = Day14()
    day14.part1()
    day14.part2()
}