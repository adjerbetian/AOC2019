package intCode

val instructions: Array<Instruction> = arrayOf(
    Add(),
    Multiply(),
    Input(),
    Output(),
    JumpIfTrue(),
    JumpIfFalse(),
    LessThan(),
    Equals()
)

interface Instruction {
    fun matches(opcode: Int): Boolean
    fun run(computer: IntCodeComputer)
}

class Add : Instruction {
    override fun matches(opcode: Int) = opcode == 1

    override fun run(computer: IntCodeComputer) {
        computer.memory[computer.getParam(3)] = computer.getParamValue(1) + computer.getParamValue(2)
        computer.instructionPointer += 4
    }
}

class Multiply : Instruction {
    override fun matches(opcode: Int) = opcode == 2

    override fun run(computer: IntCodeComputer) {
        computer.memory[computer.getParam(3)] = computer.getParamValue(1) * computer.getParamValue(2)
        computer.instructionPointer += 4
    }
}

class Input : Instruction {
    override fun matches(opcode: Int) = opcode == 3

    override fun run(computer: IntCodeComputer) {
        computer.memory[computer.getParam(1)] = computer.waitForNextInput()
        computer.instructionPointer += 2
    }
}

class Output : Instruction {
    override fun matches(opcode: Int) = opcode == 4

    override fun run(computer: IntCodeComputer) {
        computer.outputs.add(computer.getParamValue(1))
        computer.outputFunction(computer.getParamValue(1))
        computer.instructionPointer += 2
    }
}

class JumpIfTrue : Instruction {
    override fun matches(opcode: Int) = opcode == 5

    override fun run(computer: IntCodeComputer) {
        if (computer.getParamValue(1) != 0)
            computer.instructionPointer = computer.getParamValue(2)
        else
            computer.instructionPointer += 3
    }
}

class JumpIfFalse : Instruction {
    override fun matches(opcode: Int) = opcode == 6

    override fun run(computer: IntCodeComputer) {
        if (computer.getParamValue(1) == 0)
            computer.instructionPointer = computer.getParamValue(2)
        else
            computer.instructionPointer += 3
    }
}

class LessThan : Instruction {
    override fun matches(opcode: Int) = opcode == 7

    override fun run(computer: IntCodeComputer) {
        computer.memory[computer.getParam(3)] = if (computer.getParamValue(1) < computer.getParamValue(2)) 1 else 0
        computer.instructionPointer += 4
    }
}

class Equals : Instruction {
    override fun matches(opcode: Int) = opcode == 8

    override fun run(computer: IntCodeComputer) {
        computer.memory[computer.getParam(3)] = if (computer.getParamValue(1) == computer.getParamValue(2)) 1 else 0
        computer.instructionPointer += 4
    }
}
