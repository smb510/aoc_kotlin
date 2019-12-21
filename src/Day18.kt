import base.Problem

class Day18 : Problem("day18.txt") {

    enum class Type {
        OPEN, TREE, LUMBER
    }

    fun getAdjacent(board: List<List<Type>>, posY: Int, posX: Int): List<Type> {
        val minRow = Math.max(0, posY - 1)
        val maxRow = Math.min(board.size - 1, posY + 1)
        val maxCol = Math.min(board[0].size - 1, posX + 1)
        val minCol = Math.max(0, posX - 1)


        val neighbors = mutableListOf<Type>()

        for (row in minRow..maxRow) {
            for (col in minCol..maxCol) {
                if (row != posY || col != posX) {
//                    println("${board[row][col]} @ $row $col")
                    neighbors.add(board[row][col])
                }
            }
        }
        return neighbors
    }

    fun transform(plot: Type, neighbors: List<Type>): Type {
        return when (plot) {
            Type.OPEN -> if (neighbors.filter { it == Type.TREE }.size >= 3) Type.TREE else Type.OPEN
            Type.TREE -> if (neighbors.filter { it == Type.LUMBER }.size >= 3) Type.LUMBER else Type.TREE
            Type.LUMBER -> if (neighbors.filter { it == Type.LUMBER }.isNotEmpty() && neighbors.filter { it == Type.TREE }.isNotEmpty()) Type.LUMBER else Type.OPEN
        }
    }

    fun printBoard(board: List<List<Type>>) {
        board.forEach { row ->
            println(row.map {
                when (it) {
                    Type.TREE -> "|"
                    Type.OPEN -> "."
                    Type.LUMBER -> "#"
                }
            }.joinToString(""))
        }
    }

    override fun part1() {
        var board = getLines().map { row ->
            return@map row.map { col ->
                when (col) {
                    '.' -> Type.OPEN
                    '|' -> Type.TREE
                    '#' -> Type.LUMBER
                    else -> Type.OPEN
                }
            }
        }
        println(getAdjacent(board, 0, 0))
        for (i in 0 until 10) {
            board = board.withIndex().map { row ->
                row.value.withIndex().map { col ->
                    transform(col.value, getAdjacent(board, row.index, col.index))
                }
            }
            println("$i")
            printBoard(board)
        }
        println("Result: ${getResourceValue(board)}")
    }

    fun getResourceValue(board: List<List<Type>>): Int {
        val flatBoard = board.flatMap { it }
        return flatBoard.filter { it == Type.LUMBER }.size * flatBoard.filter { it == Type.TREE }.size
    }

    override fun part2() {
        val values = mutableMapOf<Int, Int>()
        var board = getLines().map { row ->
            return@map row.map { col ->
                when (col) {
                    '.' -> Type.OPEN
                    '|' -> Type.TREE
                    '#' -> Type.LUMBER
                    else -> Type.OPEN
                }
            }
        }
        println(getAdjacent(board, 0, 0))
        for (i in 0 until 1000000000) {
            if (i % 1000 == 0) {
                println("$i")
            }
            board = board.withIndex().map { row ->
                row.value.withIndex().map { col ->
                    transform(col.value, getAdjacent(board, row.index, col.index))
                }
            }
            val resourceValue = getResourceValue(board)
            if (values[resourceValue] != null) {
                println("There is a cycle: $resourceValue was found at $i and ${values[resourceValue]}")
            } else {
                values[resourceValue] = i
            }
        }
    }

}

fun main(args: Array<String>) {
    val day18 = Day18()
//    day18.part1()
    day18.part2()
}