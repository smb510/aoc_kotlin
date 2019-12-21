package year2019

import base.Problem
import java.util.*
import kotlin.math.abs

class Day3: Problem("year2019/day3.txt") {

    override fun part1() {
        val points = getLines().map {
            getPoints(it)
        }

        val intersections = points[0].intersect(points[1]).sortedBy { abs(it.first) + abs(it.second) }
        println(intersections)
    }

    override fun part2() {
        val points = getLines().map {
            getTriples(it)
        }

        val intersections = points[0].intersect(points[1]).sortedBy { abs(it.first) + abs(it.second) }
        val reverseIntersections = points[1].intersect(points[0]).sortedBy { abs(it.first) + abs(it.second) }

        println(intersections.zip(reverseIntersections).map { it.second.third + it.first.third }.sorted())

    }

    private fun getPoints(line: String) : Set<Pair<Int, Int>> {
        val points = mutableSetOf<Pair<Int, Int>>()
        val directions = line.split(",")
        var x = 0
        var y = 0
        points.add(Pair(x,y))
        directions.forEach {
            when (it[0]) {
                'R' -> {
                    val value = it.substring(1).toInt()
                    for(l in value downTo 1) {
                        x++
                        points.add(Pair(x,y))
                    }
                }
                'L' -> {
                    val value = it.substring(1).toInt()
                    for (l in value downTo 1) {
                        x--
                        points.add(Pair(x,y))
                    }
                }
                'U' -> {
                    val value = it.substring(1).toInt()
                    for (l in value downTo 1) {
                        y++
                        points.add(Pair(x,y))
                    }
                }
                'D' -> {
                    val value = it.substring(1).toInt()
                    for (l in value downTo 1) {
                        y--
                        points.add(Pair(x,y))
                    }
                }
            }
        }
        return points
    }

    data class DistanceTriple(val first: Int, val second: Int, val third: Int) {
        override fun equals(other: Any?): Boolean {
            if (other !is DistanceTriple) return false
            return other.first == first && other.second == second
        }

        override fun hashCode(): Int {
            return Objects.hash(first, second)
        }
    }

    private fun getTriples(line: String) : Set<DistanceTriple>{
        val points = mutableSetOf<DistanceTriple>()
        val directions = line.split(",")
        var x = 0
        var y = 0
        var distance = 0
        points.add(DistanceTriple(x,y, distance))
        directions.forEach {
            when (it[0]) {
                'R' -> {
                    val value = it.substring(1).toInt()
                    for(l in value downTo 1) {
                        x++
                        points.add(DistanceTriple(x,y, ++distance))
                    }
                }
                'L' -> {
                    val value = it.substring(1).toInt()
                    for (l in value downTo 1) {
                        x--
                        points.add(DistanceTriple(x,y,++distance))
                    }
                }
                'U' -> {
                    val value = it.substring(1).toInt()
                    for (l in value downTo 1) {
                        y++
                        points.add(DistanceTriple(x,y, ++distance))
                    }
                }
                'D' -> {
                    val value = it.substring(1).toInt()
                    for (l in value downTo 1) {
                        y--
                        points.add(DistanceTriple(x,y, ++distance))
                    }
                }
            }
        }
        return points
    }

}

fun main() {
    val day3 = Day3()
    day3.part1()
    day3.part2()
}