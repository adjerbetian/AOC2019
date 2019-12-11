package day2

const val ADD = 1;
const val MULT = 2;
const val END = 99;


fun treatOpcode(program: IntArray, position: Int) {
    if (program[position] == END) return;

    if (program[position] == ADD) {
        program[program[position + 3]] = program[program[position + 1]] + program[program[position + 2]];
    } else if (program[position] == MULT) {
        program[program[position + 3]] = program[program[position + 1]] * program[program[position + 2]];
    }

    return treatOpcode(program, position + 4);
}