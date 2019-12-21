package base

import java.io.File

open class Problem(fileName: String) {
    val file: File = File(filePath + "/" + fileName)

    fun getLines() : List<String> {
        return file.readLines()
    }

    open fun part1() {
        println("implement me daddy")
    }

    open fun part2() {
        println("implement me 2 daddy")
    }

    companion object {
        val filePath = System.getProperty("user.dir") + "/files"
    }
}

fun main(args: Array<String>) {
    val problem = Problem("day1.txt")
    problem.part1()
    problem.part2()
}