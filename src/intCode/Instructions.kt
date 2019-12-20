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

interface Instruction {
    fun matches(opcode: Int): Boolean
    fun run(computer: IntCodeComputer)
}

class Add : Instruction {
    override fun matches(opcode: Int) = opcode == 1

    override fun run(computer: IntCodeComputer) {
        val a = computer.getParamValue(1)
        val b = computer.getParamValue(2)
        val to = computer.getParam(3)
        computer.memory[to] = a + b

        computer.instructionPointer += 4
    }
}

class Multiply : Instruction {
    override fun matches(opcode: Int) = opcode == 2

    override fun run(computer: IntCodeComputer) {
        val a = computer.getParamValue(1)
        val b = computer.getParamValue(2)
        val to = computer.getParam(3)
        computer.memory[to] = a * b

        computer.instructionPointer += 4
    }
}

class Input : Instruction {
    override fun matches(opcode: Int) = opcode == 3

    override fun run(computer: IntCodeComputer) {
        val input = computer.waitForNextInput()
        val to = computer.getParam(1)
        computer.memory[to] = input

        computer.instructionPointer += 2
    }
}

class Output : Instruction {
    override fun matches(opcode: Int) = opcode == 4

    override fun run(computer: IntCodeComputer) {
        val output = computer.getParamValue(1)
        computer.outputs.add(output)
        computer.outputFunction(output)

        computer.instructionPointer += 2
    }
}

class JumpIfTrue : Instruction {
    override fun matches(opcode: Int) = opcode == 5

    override fun run(computer: IntCodeComputer) {
        if (computer.getParamValue(1) != 0) {
            val jump = computer.getParamValue(2)
            computer.instructionPointer = jump
        } else
            computer.instructionPointer += 3
    }
}

class JumpIfFalse : Instruction {
    override fun matches(opcode: Int) = opcode == 6

    override fun run(computer: IntCodeComputer) {
        if (computer.getParamValue(1) == 0) {
            val jump = computer.getParamValue(2)
            computer.instructionPointer = jump
        } else
            computer.instructionPointer += 3
    }
}

class LessThan : Instruction {
    override fun matches(opcode: Int) = opcode == 7

    override fun run(computer: IntCodeComputer) {
        val a = computer.getParamValue(1)
        val b = computer.getParamValue(2)
        val to = computer.getParam(3)
        computer.memory[to] = if (a < b) 1 else 0

        computer.instructionPointer += 4
    }
}

class Equals : Instruction {
    override fun matches(opcode: Int) = opcode == 8

    override fun run(computer: IntCodeComputer) {
        val a = computer.getParamValue(1)
        val b = computer.getParamValue(2)
        val to = computer.getParam(3)
        computer.memory[to] = if (a == b) 1 else 0

        computer.instructionPointer += 4
    }
}

class AdjustRelativeBase : Instruction {
    override fun matches(opcode: Int) = opcode == 9

    override fun run(computer: IntCodeComputer) {
        computer.relativeBase += computer.getParamValue(1)
        computer.instructionPointer += 2
    }
}
