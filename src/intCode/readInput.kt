package intCode

import java.io.File

fun readIntCodeProgram(path: String): IntCodeProgram {
    return File(path)
        .readText(Charsets.UTF_8)
        .split(",")
        .map { it.toInt() }
        .toIntArray()
}
