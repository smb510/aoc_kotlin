package year2019

import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class IntcodeComputer {
    var terminated = false
    val range = 1..1000
    private val memory: MutableList<Long>
    var instruction = 0L
    var output: Long? = null
    var input: Long? = null
    var phaseSetting: Long? = null
    var relativeBase = 0L

    constructor(initial: String) {
        memory = initial.split(",").map { it.toLong() }.toMutableList()
        memory.addAll(range.map { 0L })
    }


    constructor(initial: String, phaseSetting: Long) : this(initial) {
        this.phaseSetting = phaseSetting
    }

    private fun getOpcode(address: Long): Long {
        return memory[address.toInt()] % 100
    }

    @Throws(IllegalStateException::class)
    private fun process(indexLong: Long): Long {
        val index = indexLong.toInt()
        return when (getOpcode(indexLong)) {
            99L -> {
                terminate()
            }
            1L -> {
                add(memory[index], memory[index + 1], memory[index + 2], memory[index + 3])
            }
            2L -> {
                mul(memory[index], memory[index + 1], memory[index + 2], memory[index + 3])
            }
            3L -> {
                input(memory[index], memory[index + 1])
            }
            4L -> {
                output(memory[index], memory[index + 1])
            }
            5L -> {
                jit(memory[index], memory[index + 1], memory[index + 2])
            }
            6L -> {
                jif(memory[index], memory[index + 1], memory[index + 2])
            }
            7L -> {
                lt(memory[index], memory[index + 1], memory[index + 2], memory[index + 3])
            }
            8L -> {
                eq(memory[index], memory[index + 1], memory[index + 2], memory[index + 3])
            }
            9L -> {
                arb(memory[index], memory[index + 1])
            }
            else -> {
                throw IllegalStateException("Encountered illegal opcode ${memory[index]} at $index")
            }
        }
    }

    fun simulate(input2: Long): Long {
        input = input2
        var offset = process(instruction)
        while (output == null) {
            instruction += offset
            offset = process(instruction)
        }
        val out = output
        output = null
        return out ?: -1
    }

    fun execute(): Long {
        var offset = process(instruction)
        while (!terminated) {
            instruction += offset
            println("Executing $instruction")
            offset = process(instruction)
        }
        val out = output
        output = null
        return out ?: -1
    }

    private fun terminate(): Long {
        terminated = true
        output = input
        println("Terminated!")
        return -1
    }

    private fun output(opcode: Long, arg1: Long): Long {
        val positions = opcode / 100
        val p1 = getParameterValue(positions % 10, arg1)
        output = p1
        println("$output")
        return 2
    }

    private fun input(opcode: Long, arg1: Long): Long {
        val positions = opcode / 100
        val p1 =  when (positions % 10) {
            0L -> memory[arg1.toInt()]
            1L -> arg1
            2L -> (relativeBase + arg1)
            else -> throw IllegalArgumentException("unexpected parameter mode: $opcode $arg1")
        }
        val inp: Long?
        when {
            phaseSetting != null -> {
                inp = phaseSetting!!
                phaseSetting = null
            }
            input != null -> {
                inp = input
                input = null
            }
            else -> {
                throw IllegalStateException("neither phase setting nor input are nonnull")
            }
        }
        memory[p1.toInt()] = 2
        return 2
    }

    private fun add(opcode: Long, arg1: Long, arg2: Long, arg3: Long): Long {
        var positions = opcode / 100
        val p1 = getParameterValue(positions % 10, arg1)
        positions /= 10
        val p2 = getParameterValue(positions % 10, arg2)
        positions /= 10
        val p3 = getFinalParameterValue(positions % 10, arg3)
        memory[p3.toInt()] = p1 + p2
        return 4
    }

    private fun lt(opcode: Long, arg1: Long, arg2: Long, arg3: Long): Long {
        var positions = opcode / 100
        val p1 = getParameterValue(positions % 10, arg1)
        positions /= 10
        val p2 = getParameterValue(positions % 10, arg2)
        positions /= 10
        val p3 = getFinalParameterValue(positions % 10, arg3)
        memory[p3.toInt()] = if (p1 < p2) 1 else 0
        return 4
    }

    private fun eq(opcode: Long, arg1: Long, arg2: Long, arg3: Long): Long {
        var positions = opcode / 100
        val p1 = getParameterValue(positions % 10, arg1)
        positions /= 10
        val p2 = getParameterValue(positions % 10, arg2)
        positions /= 10
        val p3 = getFinalParameterValue(positions % 10, arg3)
        memory[p3.toInt()] = if (p1 == p2) 1 else 0
        return 4
    }

    private fun mul(opcode: Long, arg1: Long, arg2: Long, arg3: Long): Long {
        var positions = opcode / 100
        val p1 = getParameterValue(positions % 10, arg1)
        positions /= 10
        val p2 = getParameterValue(positions % 10, arg2)
        positions /= 10
        val p3 = getFinalParameterValue(positions % 10, arg3)
        memory[p3.toInt()] = p1 * p2
        return 4
    }

    private fun jit(opcode: Long, arg1: Long, arg2: Long): Long {
        var positions = opcode / 100
        val p1 = getParameterValue(positions % 10, arg1)
        positions /= 10
        val p2 = getParameterValue(positions % 10, arg2)
        if (p1 != 0L) {
            instruction = p2
            return 0
        }
        return 3
    }

    private fun jif(opcode: Long, arg1: Long, arg2: Long): Long {
        var positions = opcode / 100
        val p1 = getParameterValue(positions % 10, arg1)
        positions /= 10
        val p2 = getParameterValue(positions % 10, arg2)
        if (p1 == 0L) {
            instruction = p2
            return 0
        }
        return 3
    }

    private fun arb(opcode: Long, arg1: Long): Long {
        val positions = opcode / 100
        val p1 = getParameterValue(positions % 10, arg1)
        relativeBase += p1
        return 2
    }


    private fun getFinalParameterValue(mode: Long, arg1: Long): Long {
        return when (mode) {
            2L -> (relativeBase + arg1)
            else -> arg1
        }
    }

    private fun getParameterValue(mode: Long, arg1: Long): Long {
        return when (mode) {
            0L -> memory[arg1.toInt()]
            1L -> arg1
            2L -> memory[(relativeBase + arg1).toInt()]
            else -> throw IllegalArgumentException("unexpected parameter mode: $mode $arg1")
        }
    }
}