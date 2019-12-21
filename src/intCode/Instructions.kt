package intCode

val instructions: Array<Instruction> = arrayOf(
    Add(),
    Multiply(),
    Input(),
    Output(),
    JumpIfTrue(),
    JumpIfFalse(),
    LessThan(),
    Equals(),
    AdjustRelativeBase()
)

abstract class Instruction(private val opcode: Int) {
    fun matches(code: Int) = opcode == code

    abstract fun run(computer: IntCodeComputer)
}

class Add() : Instruction(1) {
    override fun run(computer: IntCodeComputer) {
        val a = computer.getParamValue(1)
        val b = computer.getParamValue(2)
        val to = computer.getParamDest(3)
        computer.memory[to] = a + b

        computer.instructionPointer += 4
    }
}

class Multiply : Instruction(2) {
    override fun run(computer: IntCodeComputer) {
        val a = computer.getParamValue(1)
        val b = computer.getParamValue(2)
        val to = computer.getParamDest(3)
        computer.memory[to] = a * b

        computer.instructionPointer += 4
    }
}

class Input : Instruction(3) {
    override fun run(computer: IntCodeComputer) {
        val input = computer.waitForNextInput()
        val to = computer.getParamDest(1)
        computer.memory[to] = input

        computer.instructionPointer += 2
    }
}

class Output : Instruction(4) {
    override fun run(computer: IntCodeComputer) {
        val output = computer.getParamValue(1)
        computer.outputs.add(output)
        computer.outputFunction(output)

        computer.instructionPointer += 2
    }
}

class JumpIfTrue : Instruction(5) {
    override fun run(computer: IntCodeComputer) {
        if (computer.getParamValue(1) != 0L) {
            val jump = computer.getParamValue(2)
            computer.instructionPointer = jump
        } else
            computer.instructionPointer += 3
    }
}

class JumpIfFalse : Instruction(6) {
    override fun run(computer: IntCodeComputer) {
        if (computer.getParamValue(1) == 0L) {
            val jump = computer.getParamValue(2)
            computer.instructionPointer = jump
        } else
            computer.instructionPointer += 3
    }
}

class LessThan : Instruction(7) {
    override fun run(computer: IntCodeComputer) {
        val a = computer.getParamValue(1)
        val b = computer.getParamValue(2)
        val to = computer.getParamDest(3)
        computer.memory[to] = if (a < b) 1 else 0

        computer.instructionPointer += 4
    }
}

class Equals : Instruction(8) {
    override fun run(computer: IntCodeComputer) {
        val a = computer.getParamValue(1)
        val b = computer.getParamValue(2)
        val to = computer.getParamDest(3)
        computer.memory[to] = if (a == b) 1 else 0

        computer.instructionPointer += 4
    }
}

class AdjustRelativeBase : Instruction(9) {
    override fun run(computer: IntCodeComputer) {
        computer.relativeBase += computer.getParamValue(1)
        computer.instructionPointer += 2
    }
}
