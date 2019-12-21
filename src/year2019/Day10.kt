package year2019

import base.Problem
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

class Day10 : Problem("year2019/day10.txt") {

    override fun part1() {
        val asteroids = mutableListOf<Pair<Int, Int>>()
        val lines = getLines()
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { column, character ->
                if (character.toString() == "#") {
                    asteroids.add(Pair(column, row))
                }
            }
        }
        val bestPlace = asteroids.map {
            Pair(it, countLineOfSight(it, asteroids))
        }.sortedByDescending { it.second }[0]

        println(bestPlace)
    }

    override fun part2() {
        val asteroids = mutableListOf<Pair<Int, Int>>()
        val lines = getLines()
        lines.forEachIndexed { row, line ->
            line.forEachIndexed { column, character ->
                if (character.toString() == "#") {
                    asteroids.add(Pair(column, row))
                }
            }
        }
        asteroids.remove(Pair(11, 13))
        val laser = Pair(11, 13)
        val order = asteroids.map { getPolarCoordinates(laser, it) }.sortedBy { it.second }.sortedBy { it.third }.groupBy { it.third }.toSortedMap().toMutableMap()
        val smooshed = order.values.map {
            it.mapIndexed { idx, triple ->
                 Triple(triple.first, idx, triple.third)
        } }.flatten().sortedBy { it.third }.sortedBy { it.second }

        println(smooshed)
    }

    private fun countLineOfSight(asteroid: Pair<Int, Int>, roids: List<Pair<Int, Int>>): Int {
        val removed = roids.toMutableList()
        removed.remove(asteroid)
        var LoS = 0
        removed.forEach { a ->
            val lineOfSight = removed.count { b ->
                val isOnLine = isOnLineBetween(asteroid, a, b)
                if (a != b && isOnLine) {
                    return@count true
                }
                return@count false
            }
            if (lineOfSight == 0) {
                LoS++
            }
        }
        return LoS
    }

    private fun isOnLineBetween(a: Pair<Int, Int>, b: Pair<Int, Int>, c: Pair<Int, Int>): Boolean {
        val slope = (b.second - a.second).toFloat() / (b.first - a.first)
        if (slope == Float.POSITIVE_INFINITY || slope == Float.NEGATIVE_INFINITY) {
            // vertical line
            return c.first == a.first && (c.second in a.second..b.second || c.second in b.second..a.second)
        }

        if (slope == 0f) {
            //horizontal line
            return c.second == a.second && (c.first in a.first..b.first || c.first in b.first..a.first)
        }
        return (c.second - a.second).toFloat() == (slope * (c.first - a.first))
                && ((c.first in a.first..b.first) || (c.first in b.first..a.first))
                && ((c.second in a.second..b.second) || (c.second in b.second..a.second))
    }

    private fun getPolarCoordinates(laser: Pair<Int, Int>, asteroid: Pair<Int, Int>): Triple<Pair<Int, Int>, Double, Double> {
        val x = (laser.first - asteroid.first).toDouble()
        val y = (laser.second - asteroid.second).toDouble()
        val r = sqrt(y.pow(2.0) + x.pow(2.0))
        var theta = atan2(y.toFloat(), x.toFloat()) * 180 / Math.PI
        if (theta < 0) {
            theta *= -1
            theta += 180
        }
        theta += 270
        theta %= 360
        return Triple(asteroid, r, theta)
    }

}


fun main() {
    val day10 = Day10()
    day10.part2()
}