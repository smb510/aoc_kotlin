import base.Problem

class Day10 : Problem("day10.txt") {




    fun getRack(input: Int) : Array<Array<Int>> {
        val cells = Array(300){ Array(300){0}}
        for (row in 0..299) {
            for (col in 0..299) {
                val rackId = (col + 1) + 10
                var powerLevel = rackId * (row + 1)
                powerLevel += input
                powerLevel *= rackId
                powerLevel /= 100
                powerLevel %= 10
                powerLevel -= 5
                cells[row][col] = powerLevel
            }
        }
        return cells
    }

    data class Result(val sum: Int, val xCols: Int, val yRows: Int, val size: Int)

    fun findBiggestSquare(cells: Array<Array<Int>>, size: Int) : Result {
        var maxSum = -100000
        var maxRow = -1
        var maxCol = -1

        for (row in 0 until 300 - size) {
            for (col in 0 until 300 - size) {
                var sum = 0
                for (i in row until row+size) {
                    sum += cells[i].sliceArray(col until col + size).sum()
                }
                if (sum > maxSum) {
                    maxSum = sum
                    maxRow = row
                    maxCol = col
                }
            }
        }
        return Result(maxSum, maxCol + 1, maxRow + 1, size)
    }


    override fun part1() {
        val cells = getRack(5093)
        val result = findBiggestSquare(cells, 3)
        println("x: ${result.xCols}, y: ${result.yRows}, sum: ${result.sum}")
    }

    override fun part2() {
        var cells = getRack(5093)
        var results = mutableListOf<Result>()
        for (size in 1..300) {
            println("size: $size")
            results.add(findBiggestSquare(cells, size))
        }
        results.sortByDescending { it.sum  }
        val biggest = results[0]
        println("${biggest.sum}, x: ${biggest.xCols}, y: ${biggest.yRows}, size: ${biggest.size}")
    }
}

fun main(args: Array<String>) {
    val day10 = Day10()
    day10.part1()
    day10.part2()
}