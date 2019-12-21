import base.Problem

class Day13 : Problem("day13.txt") {

    enum class Orientation {
        N, E, S, W
    }

    enum class Turn {
        LEFT, STRAIGHT, RIGHT
    }

    class Cart(var orientation: Orientation, var positionX: Int, var positionY: Int) {
        var nextTurn = Turn.LEFT

        override fun toString(): String {
            return "Cart: ($positionX, $positionY) heading $orientation nextTurn: $nextTurn"
        }

        fun turnClockwise() {
            this.orientation = when (orientation) {
                Orientation.N -> Orientation.E
                Orientation.E -> Orientation.S
                Orientation.S -> Orientation.W
                Orientation.W -> Orientation.N
            }
        }

        fun turnAntiClockwise() {
            this.orientation = when (orientation) {
                Orientation.N -> Orientation.W
                Orientation.E -> Orientation.N
                Orientation.S -> Orientation.E
                Orientation.W -> Orientation.S
            }
        }

        fun move(track: List<List<Track>>) {
            when (this.orientation) {
                Orientation.N -> positionY--
                Orientation.S -> positionY++
                Orientation.E -> positionX++
                Orientation.W -> positionX--
            }
            val newTrack = track[positionY][positionX]
            when (newTrack) {
                Track.INTERSECT -> turnAtIntersection()
                Track.TURN_L -> turnAtCurve(newTrack)
                Track.TURN_R -> turnAtCurve(newTrack)
                else -> {}
            }
        }

        fun turnAtIntersection() {
            when (nextTurn) {
                Turn.STRAIGHT -> { nextTurn = Turn.RIGHT}
                Turn.LEFT -> {
                    turnAntiClockwise()
                    nextTurn = Turn.STRAIGHT
                }
                Turn.RIGHT -> {
                    turnClockwise()
                    nextTurn = Turn.LEFT
                }
            }
        }
        fun turnAtCurve(curve: Track) {
            when (curve) {
                Track.TURN_L -> {
                    orientation = when (orientation) {
                        Orientation.N -> Orientation.E
                        Orientation.E -> Orientation.N
                        Orientation.W -> Orientation.S
                        Orientation.S -> Orientation.W
                    }
                }
                Track.TURN_R -> {
                    orientation = when (orientation) {
                        Orientation.N -> Orientation.W
                        Orientation.E -> Orientation.S
                        Orientation.W -> Orientation.N
                        Orientation.S -> Orientation.E
                    }
                }
                else -> {}
            }

        }

    }

    enum class Track {
        STRAIGHT_H, STRAIGHT_V, INTERSECT, TURN_L, TURN_R, NONE
    }

    override fun part1() {
        val lines = getLines()
        val tracks = lines.map { listLine ->
            return@map listLine.map { letter ->
                return@map when (letter) {
                    ' ' -> Track.NONE
                    '-' -> Track.STRAIGHT_H
                    '|' -> Track.STRAIGHT_V
                    '+' -> Track.INTERSECT
                    '>' -> Track.STRAIGHT_H
                    '<' -> Track.STRAIGHT_H
                    '^' -> Track.STRAIGHT_V
                    'v' -> Track.STRAIGHT_V
                    '/' -> Track.TURN_L
                    '\\' -> Track.TURN_R
                    else -> Track.NONE
                }
            }
        }
        val carts = lines.withIndex().map {
            listLine ->
                return@map listLine.value.withIndex().map { letter ->
                    return@map when (letter.value) {
                        '>' -> Cart(Orientation.E, letter.index, listLine.index)
                        '<' -> Cart(Orientation.W, letter.index, listLine.index)
                        'v' -> Cart(Orientation.S, letter.index, listLine.index)
                        '^' -> Cart(Orientation.N, letter.index, listLine.index)
                        else -> null
                    }
                }.filterNotNull()
        }.flatMap { it }
        var iterations = 0
        while (!isCollision(carts)) {
            print("$iterations: ")
                carts.forEach { it -> it.move(tracks)
            }
            println(carts)
            iterations++
        }
        val location = carts.map { it -> Pair(it.positionX, it.positionY) }.groupingBy { it }.eachCount().filter { it.value > 1 }
        println("at $iterations a crash happened at $location")

    }

    override fun part2() {
        val lines = getLines()
        val tracks = lines.map { listLine ->
            return@map listLine.map { letter ->
                when (letter) {
                    ' ' -> Track.NONE
                    '-' -> Track.STRAIGHT_H
                    '|' -> Track.STRAIGHT_V
                    '+' -> Track.INTERSECT
                    '>' -> Track.STRAIGHT_H
                    '<' -> Track.STRAIGHT_H
                    '^' -> Track.STRAIGHT_V
                    'v' -> Track.STRAIGHT_V
                    '/' -> Track.TURN_L
                    '\\' -> Track.TURN_R
                    else -> Track.NONE
                }
            }
        }
        var carts = lines.withIndex().map {
            listLine ->
            return@map listLine.value.withIndex().map { letter ->
                when (letter.value) {
                    '>' -> Cart(Orientation.E, letter.index, listLine.index)
                    '<' -> Cart(Orientation.W, letter.index, listLine.index)
                    'v' -> Cart(Orientation.S, letter.index, listLine.index)
                    '^' -> Cart(Orientation.N, letter.index, listLine.index)
                    else -> null
                }
            }.filterNotNull()
        }.flatMap { it }
        var iterations = 0
        while (carts.size > 1) {
            while (!isCollision(carts)) {
                print("$iterations: ")
                carts.forEach { it ->
                    it.move(tracks)
                }
                iterations++
            }
            val location = carts.map { it -> Pair(it.positionX, it.positionY) }.groupingBy { it }.eachCount().filter { it.value > 1 }.entries.toList().get(0)
            carts = carts.filterNot { it.positionX == location.key.first && it.positionY == location.key.second }
            println("at $iterations a crash happened at $location")
            println("There are not ${carts.size} carts")
        }
        println("Just one left: ${carts[0]}")
    }


    fun isCollision(carts: List<Cart>) : Boolean {
       return carts.map { it -> Pair(it.positionX, it.positionY) }.toSet().size < carts.size
    }

}

fun main(args: Array<String>) {
    val day13 = Day13()
    day13.part1()
    day13.part2()
}