import base.Problem

class Day7_2015 : Problem("2015_day7.txt") {

    private val satisfiedRegisters = mutableMapOf<String, Int>()

    override fun part1() {
        val instructions = getLines()
        var unparsed = instructions.filter { !parse(it) }
        while (unparsed.isNotEmpty()) {
            println("${unparsed.size} still to parse")
            unparsed = unparsed.filter { !parse(it) }
        }
        println("Value ${satisfiedRegisters["a"]}")
    }

    override fun part2() {
        part1()
        val b = satisfiedRegisters["a"]
        satisfiedRegisters.clear()
        satisfiedRegisters["b"] = b!!
        part1()

    }

    fun parse(line: String): Boolean {
        val sides = line.split(" -> ")
        val lhs = sides[0].trim()
        val rhs = sides[1].trim()
        if (satisfiedRegisters[rhs] != null) {
            return true
        }
        val lhsVal = getLhs(lhs)
        if (lhsVal != null) {
            satisfiedRegisters[rhs] = lhsVal
            return true
        }
        return false
    }

    fun getLhs(lhs: String): Int? {
        return when {
            lhs.contains("AND") -> parseAnd(lhs)
            lhs.contains("OR") -> parseOr(lhs)
            lhs.contains("NOT") -> parseNot(lhs)
            lhs.contains("LSHIFT") -> parseLShift(lhs)
            lhs.contains("RSHIFT") -> parseRShift(lhs)
            lhs.toIntOrNull() != null -> parseConst(lhs)
            else -> parseRegister(lhs)

        }
    }

    fun parseRegister(lhs: String) : Int? {
        return satisfiedRegisters[lhs.trim()]
    }

    fun parseConst(lhs: String) : Int? {
        return lhs.trim().toInt()
    }

    fun parseRShift(lhs: String) : Int? {
        val args = lhs.split(" RSHIFT ")
        val arg1 = args[1]
        val arg0 = args[0]
        return satisfiedRegisters[arg0]?.shr(arg1.toInt())
    }

    fun parseLShift(lhs: String) : Int? {
        val args = lhs.split(" LSHIFT ")
        val arg1 = args[1]
        val arg0 = args[0]
        return satisfiedRegisters[arg0]?.shl(arg1.toInt())
    }

    fun parseNot(lhs: String): Int? {
        val args = lhs.split("NOT ")
        val arg1 = args[1]
        return (satisfiedRegisters[arg1])?.inv()
    }


    fun parseAnd(lhs: String): Int? {
        val args = lhs.split(" AND ")
        val arg1 = args[1]
        return if (args[0].toIntOrNull() != null) {
            satisfiedRegisters[arg1]?.and(args[0].toInt())
        } else {
            if (satisfiedRegisters[arg1] != null && satisfiedRegisters[args[0]] != null) {
                return satisfiedRegisters[args[0]]?.and(satisfiedRegisters[arg1]!!)
            }
            return null
        }
    }

    fun parseOr(lhs: String): Int? {
        val args = lhs.split(" OR ")
        val arg1 = args[1]
        return if (args[0].toIntOrNull() != null) {
            satisfiedRegisters[arg1]?.or(args[0].toInt())
        } else {
            if (satisfiedRegisters[arg1] != null && satisfiedRegisters[args[0]] != null) {
                return satisfiedRegisters[args[0]]?.or(satisfiedRegisters[arg1]!!)
            }
            return null
        }
    }
}

fun main(args: Array<String>) {
    val prob = Day7_2015()
    prob.part2()
}