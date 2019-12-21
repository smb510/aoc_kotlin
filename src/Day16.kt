import base.Problem

class Day16 : Problem("day16.txt") {

    fun addr(registers: MutableList<Int>, regA: Int, regB: Int, regC: Int): List<Int> {
        registers[regC] = registers[regA] + registers[regB]
        return registers
    }

    fun addi(registers: MutableList<Int>, regA: Int, valB: Int, regC: Int): List<Int> {
        registers[regC] = registers[regA] + valB
        return registers
    }

    fun mulr(registers: MutableList<Int>, regA: Int, regB: Int, regC: Int): List<Int> {
        registers[regC] = registers[regA] * registers[regB]
        return registers
    }

    fun muli(registers: MutableList<Int>, regA: Int, valB: Int, regC: Int): List<Int> {
        registers[regC] = registers[regA] * valB
        return registers
    }

    fun banr(registers: MutableList<Int>, regA: Int, regB: Int, regC: Int): List<Int> {
        registers[regC] = registers[regA].and(registers[regB])
        return registers
    }

    fun bani(registers: MutableList<Int>, regA: Int, valB: Int, regC: Int): List<Int> {
        registers[regC] = registers[regA].and(valB)
        return registers
    }

    fun borr(registers: MutableList<Int>, regA: Int, regB: Int, regC: Int): List<Int> {
        registers[regC] = registers[regA].or(registers[regB])
        return registers
    }

    fun bori(registers: MutableList<Int>, regA: Int, valB: Int, regC: Int): List<Int> {
        registers[regC] = registers[regA].or(valB)
        return registers
    }

    fun setr(registers: MutableList<Int>, regA: Int, unusedB: Int, regC: Int): List<Int> {
        registers[regC] = registers[regA]
        return registers
    }

    fun seti(registers: MutableList<Int>, valA: Int, unusedB: Int, regC: Int): List<Int> {
        registers[regC] = valA
        return registers
    }

    fun gtir(registers: MutableList<Int>, valA: Int, regB: Int, regC: Int): List<Int> {
        registers[regC] = if (valA > registers[regB]) 1 else 0
        return registers
    }

    fun gtri(registers: MutableList<Int>, regA: Int, valB: Int, regC: Int): List<Int> {
        registers[regC] = if (registers[regA] > valB) 1 else 0
        return registers
    }

    fun gtrr(registers: MutableList<Int>, regA: Int, regB: Int, regC: Int): List<Int> {
        registers[regC] = if (registers[regA] > registers[regB]) 1 else 0
        return registers
    }

    fun eqir(registers: MutableList<Int>, valA: Int, regB: Int, regC: Int): List<Int> {
        registers[regC] = if (valA == registers[regB]) 1 else 0
        return registers
    }

    fun eqri(registers: MutableList<Int>, regA: Int, valB: Int, regC: Int): List<Int> {
        registers[regC] = if (registers[regA] == valB) 1 else 0
        return registers
    }

    fun eqrr(registers: MutableList<Int>, regA: Int, regB: Int, regC: Int): List<Int> {
        registers[regC] = if (registers[regA] == registers[regB]) 1 else 0
        return registers
    }

    fun matches(before: MutableList<Int>, codes: List<Int>, after: List<Int>): Int {
        val functions = listOf(::addi, ::addr, ::muli, ::mulr, ::banr, ::bani, ::borr, ::bori, ::setr, ::seti, ::gtir, ::gtri, ::gtrr, ::eqir, ::eqri, ::eqrr)
        val matches = functions.map {
            it(before.toMutableList(), codes[1], codes[2], codes[3]) == after
        }
        return matches.filter { it }.size

    }

    fun opCodeMatch(before: MutableList<Int>, codes: List<Int>, after: List<Int>): Pair<Int, List<String>> {
        val functions = listOf(::addi, ::addr, ::muli, ::mulr, ::banr, ::bani, ::borr, ::bori, ::setr, ::seti, ::gtir, ::gtri, ::gtrr, ::eqir, ::eqri, ::eqrr)
        val matches = functions.map {
            it(before.toMutableList(), codes[1], codes[2], codes[3]) == after
        }.zip(functions).filter { !it.first }.map { it.second.name }
        val opcode = codes[0]
        return Pair(opcode, matches)
    }

    fun runProgram() {

    }


    override fun part1() {
        val lines = getLines()
        val regex = Regex(".*(\\d+), (\\d+), (\\d+), (\\d+).*")
        var gt3 = 0
        for (x in 0..3144 step 4) {
            val before = regex.find(lines[x])!!.groupValues.drop(1).map { it.toInt() }.toMutableList()
            val codes = lines[x + 1].split(" ").map { it.toInt() }
            val after = regex.find(lines[x + 2])!!.groupValues.drop(1).map { it.toInt() }
            if (matches(before, codes, after) >= 3) {
                gt3++
            }
        }
        println("$gt3")
    }

    fun compute(registers: MutableList<Int>, opCode: List<Int>) {
        when (opCode[0]) {
            0 -> eqri(registers, opCode[1], opCode[2], opCode[3])
            1 -> mulr(registers, opCode[1], opCode[2], opCode[3])
            2 -> gtri(registers, opCode[1], opCode[2], opCode[3])
            3 -> gtrr(registers, opCode[1], opCode[2], opCode[3])
            4 -> banr(registers, opCode[1], opCode[2], opCode[3])
            5 -> addi(registers, opCode[1], opCode[2], opCode[3])
            6 -> seti(registers, opCode[1], opCode[2], opCode[3])
            7 -> gtir(registers, opCode[1], opCode[2], opCode[3])
            8 -> muli(registers, opCode[1], opCode[2], opCode[3])
            9 -> bori(registers, opCode[1], opCode[2], opCode[3])
            10 -> setr(registers, opCode[1], opCode[2], opCode[3])
            11 -> addr(registers, opCode[1], opCode[2], opCode[3])
            12 -> bani(registers, opCode[1], opCode[2], opCode[3])
            13 -> borr(registers, opCode[1], opCode[2], opCode[3])
            14 -> eqir(registers, opCode[1], opCode[2], opCode[3])
            15 -> eqrr(registers, opCode[1], opCode[2], opCode[3])
        }
    }

    override fun part2() {
        val lines = getLines()
        val registers = mutableListOf(0, 0, 0, 0)
        for (x in 3146..lines.size - 1) {
            compute(registers, lines[x].split(" ").map { it.toInt() })
        }
        println("${registers}")

    }
}

fun main(args: Array<String>) {
    val day16 = Day16()
    day16.part2()
}