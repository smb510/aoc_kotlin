import base.Problem
import java.util.*

class Day15 : Problem("day15_sample.txt") {


    class Fighter(val type: Type, var locationX: Int, var locationY: Int) {
        var hitPoints = 200

        fun attack() {
            hitPoints -= 3
        }

        fun getEnemyRace(): Type {
            return if (this.type == Type.ELF) Type.GOBLIN else Type.ELF
        }

        fun getTargets(board: List<List<Square>>): MutableList<Square> {
            return board.flatten().filter { it.type == getEnemyRace() }.toMutableList()
        }

        fun getNeighbors(board: List<List<Square>>, posX: Int, posY: Int): List<Square> {
            val left = board[posY][posX - 1]
            val right = board[posY][posX + 1]
            val up = board[posY - 1][posX]
            val down = board[posY + 1][posX]
            return listOf(left, right, up, down)
        }

        fun getTargetsInRange(board: List<List<Square>>): List<Square> {
            return getNeighbors(board, locationX, locationY).filter { it.type == getEnemyRace() }
        }

        fun getOpenSquares(board: List<List<Square>>, targets: List<Square>): MutableList<Square> {
            return targets.map { sq ->
                getNeighbors(board, sq.positionX, sq.positionY).filter { it.type == Type.CAVERN }
            }.flatten().toMutableList()
        }

        fun nearestSquare(board: List<List<Square>>, openSquares: MutableList<Square>): List<List<Square>> {
            val visited = mutableSetOf<Square>()
            val queue = LinkedList<Pair<Square, Int>>()
            val root = board[this.locationY][this.locationX]
            queue.addFirst(Pair(root, 0))
            val pathMap = mutableMapOf<Square, Square>()
            val paths = mutableListOf<List<Square>>()
            while (queue.isNotEmpty() && openSquares.isNotEmpty()) {
                val node = queue.removeFirst()
                if (openSquares.contains(node.first)) {
                    openSquares.remove(node.first)
                    val path = mutableListOf<Square>()
                    var parent = pathMap[node.first]
                    while (parent != null) {
                        path.add(parent)
                        parent = pathMap[parent]
                    }
                    paths.add(path)
                    continue
                }
                val viableNeighbors =
                        getNeighbors(board, node.first.positionY, node.first.positionX).filter { it.type == Type.CAVERN }
                                .filter { !visited.contains(it) }
                viableNeighbors.forEach { pathMap[it] = node.first }
                queue.addAll(viableNeighbors.map { Pair(it, node.second + 1) })
                visited.add(node.first)
            }
            return paths
        }

        fun getSquareAction(board: List<List<Square>>): Square {
            val targetsInRange = getTargetsInRange(board)
            if (targetsInRange.isNotEmpty()) {
                return targetsInRange.sortedBy { it.positionY }.sortedBy { it.positionX }.first()
            } else {
                val pathsToSquares = nearestSquare(board, getOpenSquares(board, getTargets(board)))
                return pathsToSquares.map { it.dropLast(1).last() }.sortedBy { it.positionY }.sortedBy { it.positionX }[0]
            }
        }
    }

    enum class Type {
        WALL, CAVERN, GOBLIN, ELF
    }

    data class Square(var type: Type, var positionX: Int, var positionY: Int)


    override fun part1() {
        val lines = getLines()
        var board = lines.withIndex().map { line ->
            return@map line.value.withIndex().map { char ->
                when (char.value) {
                    '.' -> Square(Type.CAVERN, char.index, line.index)
                    '#' -> Square(Type.WALL, char.index, line.index)
                    'G' -> Square(Type.GOBLIN, char.index, line.index)
                    'E' -> Square(Type.ELF, char.index, line.index)
                    else -> Square(Type.WALL, char.index, line.index)
                }
            }.toMutableList()
        }.toMutableList()
        var combatants = lines.withIndex().map { line ->
            return@map line.value.withIndex().map { char ->
                when (char.value) {
                    'G' -> Fighter(Type.GOBLIN, char.index, line.index)
                    'E' -> Fighter(Type.ELF, char.index, line.index)
                    else -> null
                }
            }.filterNotNull()
        }.flatten().toMutableList()

        var i = 0;
        while (i < 50 && combatants.filter { it.type == Type.GOBLIN }.isNotEmpty() && combatants.filter { it.type == Type.ELF }.isNotEmpty()) {
            println(i)
            val pair = takeTurn(board, combatants)
            board = pair.first
            combatants = pair.second
            println(combatants.map { "${it.locationY} , ${it.locationX}" })
            i++
        }
        println(combatants.sumBy { it.hitPoints })
    }

    fun takeTurn(board: MutableList<MutableList<Square>>, combatants: MutableList<Fighter>): Pair<MutableList<MutableList<Square>>, MutableList<Fighter>> {
        combatants.forEach { combatant ->
            if (combatant.hitPoints > 0) {
                val nextSquare = combatant.getSquareAction(board)
                if (nextSquare.type == Type.GOBLIN || nextSquare.type == Type.ELF) {
                    // attack!
                    val victim = combatants.filter { it == Fighter(nextSquare.type, nextSquare.positionX, nextSquare.positionY) }.first()
                    victim.attack()
                } else if (nextSquare.type == Type.CAVERN) {
                    // need to move the goblin. first update the board.
                    val meFighter = board[combatant.locationY][combatant.locationX]
                    // where nextSquare used to be, put MeFighter
                    board[nextSquare.positionY][nextSquare.positionX] = meFighter;
                    board[nextSquare.positionY][nextSquare.positionX] = nextSquare;
                }
            }
        }

        combatants.forEach {
            if (it.hitPoints <= 0) {
                board[it.locationY][it.locationX] = Square(Type.CAVERN, it.locationX, it.locationY)
            }
        }
        var cmp = compareBy<Fighter> { it.locationY }
        cmp = cmp.then(compareBy { it.locationX })
        combatants.sortWith(cmp)
        return Pair(board, combatants)
    }
}

fun main(args: Array<String>) {
    val day15 = Day15()
    day15.part1()
}