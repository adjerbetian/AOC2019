package intCode

import java.io.File

fun readIntCodeProgram(path: String): IntCodeProgram {
    return File(path)
        .readText(Charsets.UTF_8)
        .split(",")
        .map { it.toLong() }
        .toLongArray()
}


fun intCodeProgramOf(vararg codes: Long): IntCodeProgram {
    return codes.toList().toLongArray()
}