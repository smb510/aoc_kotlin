package year2019

import base.Problem
import kotlin.math.abs

class Day12 : Problem("year2019/day12.txt") {


    override fun part1() {
        val io = Moon(Triple(-1, 0, 2))
        val europa = Moon(Triple(2, -10, -7))
        val ganymede = Moon(Triple(4, -8, 8))
        val callisto = Moon(Triple(3, 5, -1))

        val moons = listOf(io, europa, ganymede, callisto)
        val hashes = mutableSetOf<Int>()
        var c = 0L
        while (c >= 0) {
            if (c % 100000L == 0L) {
                println("Checkpoint $c")
            }
            update(moons)
            val hash = moons.toTypedArray().contentDeepHashCode()
            if (hashes.contains(hash)) {
                println("$c")
                print(moons)
                return
            }
            hashes.add(hash)
            c++
        }
    }

    private fun update(moons: List<Moon>) {
        for (i in moons.indices) {
            for (j in i until moons.size) {
                moons[i].applyGravity(moons[j])
                moons[j].applyGravity(moons[i])
            }
        }
        for (moon in moons) {
            moon.move()
        }
    }

    class Moon(private var position: Triple<Int, Int, Int>) {



        private var velocity = Triple(0, 0, 0)

        private fun potentialEnergy(): Int {
            return position.toList().map { abs(it) }.sum()
        }

        private fun kineticEnergy(): Int {
            return velocity.toList().map { abs(it) }.sum()
        }

        fun totalEnergy(): Int {
            return kineticEnergy() * potentialEnergy()
        }

        fun applyGravity(other: Moon) {
            val adjustments = pullGravity(position, other.position)
            val newVelocities = adjustments.toList().zip(velocity.toList()).map { it.first + it.second }
            velocity = Triple(newVelocities[0], newVelocities[1], newVelocities[2])
        }

        private fun pullGravity(vector: Triple<Int, Int, Int>, other: Triple<Int, Int, Int>): Triple<Int, Int, Int> {
            val adjusted = vector.toList().zip(other.toList()).map {
                if (it.first < it.second) {
                    return@map 1
                } else if (it.first > it.second) {
                    return@map -1
                }
                return@map 0
            }
            return Triple(adjusted[0], adjusted[1], adjusted[2])

        }

        fun move() {
            val moved = position.toList().zip(velocity.toList()).map { it.second + it.first }
            position = Triple(moved[0], moved[1], moved[2])
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Moon

            if (position != other.position) return false
            if (velocity != other.velocity) return false

            return true
        }

        override fun hashCode(): Int {
            var result = position.hashCode()
            result = 31 * result + velocity.hashCode()
            return result
        }

        override fun toString(): String {
            return "Moon(position=$position, velocity=$velocity)"
        }
    }
}

fun main() {
    val day12 = Day12()
    day12.part1()
}