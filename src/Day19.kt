import base.Problem

class Day19 : Problem("day19.txt") {
    var insn = 0
    var ip = 5
    val regex = Regex("(\\w+) (\\d+) (\\d+) (\\d+)")
    val registers = mutableListOf(0, 0, 0, 0, 0, 0)

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

    fun execute(line: String) {
        if (line.startsWith("#ip")) {
            ip = Regex("(\\d+)").find(line)!!.groupValues[1].toInt()
        } else {
            val match = regex.find(line)
            val insn = match?.groupValues?.get(1)
            val arg0 = match?.groupValues?.get(2)!!.toInt()
            val arg1 = match.groupValues[3].toInt()
            val arg2 = match.groupValues[4].toInt()
            when (insn!!) {
                "addr" -> addr(registers, arg0, arg1, arg2)
                "addi" -> addi(registers, arg0, arg1, arg2)
                "mulr" -> mulr(registers, arg0, arg1, arg2)
                "muli" -> muli(registers, arg0, arg1, arg2)
                "banr" -> banr(registers, arg0, arg1, arg2)
                "bani" -> bani(registers, arg0, arg1, arg2)
                "borr" -> borr(registers, arg0, arg1, arg2)
                "bori" -> bori(registers, arg0, arg1, arg2)
                "setr" -> setr(registers, arg0, arg1, arg2)
                "seti" -> seti(registers, arg0, arg1, arg2)
                "gtir" -> gtir(registers, arg0, arg1, arg2)
                "gtri" -> gtri(registers, arg0, arg1, arg2)
                "gtrr" -> gtrr(registers, arg0, arg1, arg2)
                "eqir" -> eqir(registers, arg0, arg1, arg2)
                "eqri" -> eqri(registers, arg0, arg1, arg2)
                "eqrr" -> eqrr(registers, arg0, arg1, arg2)
            }
        }
    }

    override fun part1() {
        val lines = getLines()
        while (insn >= 0 && insn < lines.size) {
            val line = lines[insn]
            print("ip=$insn $registers $line ")
            registers[ip] = insn
            execute(line)
            insn = registers[ip]
            if (!line.startsWith("#ip")) {
                insn++
            }
            println(registers)
        }
        println(registers)
    }
}

fun main(args: Array<String>) {
    val day19 = Day19()
    day19.part1()
}