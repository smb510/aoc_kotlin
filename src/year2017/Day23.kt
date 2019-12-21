package year2017

import base.Problem

class Day23 : Problem("year2017/day23.txt") {

    val registers = mutableMapOf<String, Int>()
    var insn = 0
    var muls = 0
    lateinit var instructions: List<String>

    override fun part1() {
        instructions = getLines()
        registers["a"] = 1
        registers["b"] = 0
        registers["c"] = 0
        registers["d"] = 0
        registers["e"] = 0
        registers["f"] = 0
        registers["g"] = 0
        registers["h"] = 0
        while (insn >= 0 && insn < instructions.size) {
            run()
        }
        println(registers)
        println(muls)
    }

    private fun run() {
        if (insn < 0 || insn >= instructions.size) {
            return
        }
        val instruction = instructions[insn]
        insn += when {
            instruction.startsWith("mul") -> {
                mul(instruction)
            }
            instruction.startsWith("set") -> {
                set(instruction)
            }
            instruction.startsWith("sub") -> {
                sub(instruction)
            }
            else -> {
                jnz(instruction)
            }
        }

    }

    private fun mul(line: String): Int {
        muls++
        val components = line.split(" ")
        val src = components[1]
        if (components[2].toIntOrNull() != null) {
            registers[src] = (registers[src])!! * components[2].toInt()
            return 1
        }
        registers[src] = registers[src]!! * registers[components[2]]!!
        return 1
    }

    private fun set(line: String): Int {
        val components = line.split(" ")
        val src = components[1]
        if (components[2].toIntOrNull() != null) {
            registers[src] = components[2].toInt()
        } else {
            registers[src] = registers[components[2]]!!
        }
        return 1
    }

    private fun sub(line: String): Int {
        val components = line.split(" ")
        val src = components[1]
        if (components[2].toIntOrNull() != null) {
            registers[src] = (registers[src])!! - components[2].toInt()
            return 1
        }
        registers[src] = registers[src]!! - registers[components[2]]!!
        return 1
    }

    private fun jnz(line: String): Int {
        val components = line.split(" ")
        val src = components[1]
        val dst = components[2]
        if (src.toIntOrNull() != null) {
            if (src.toInt() != 0) {
                return dst.toInt()
            }
        } else {
            val srcVal = registers[src]!!
            if (srcVal != 0) {
                return dst.toInt()
            }
        }
        return 1
    }

}

fun main() {
    Day23().part1()
}