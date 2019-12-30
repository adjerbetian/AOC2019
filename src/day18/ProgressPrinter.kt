package day18

import java.util.*
import kotlin.collections.HashMap

class ProgressPrinter(private val vault: Vault) {
    private val LOG_INTERVAL = 10000
    private var lastLog = Date().time
    private val counters = HashMap<Char, Long>()

    fun trackProgress(char: Char) {
        counters[char] = (counters[char] ?: 0) + 1
        printProgress()
    }

    private fun printProgress() {
        if (Date().time - lastLog > LOG_INTERVAL) {
            counters.entries.forEach { println("${it.key} : ${prettyInt(it.value)}") }
            println("best path length: ${vault.bestPathLength}")
            println("---")
            lastLog = Date().time
        }
    }

    private fun prettyInt(n: Long): String {
        return n.toString()
            .reversed()
            .map { it.toString() }
            .reduce { acc, s -> if (acc.length % 4 == 3) "$acc $s" else acc + s }
            .reversed()
    }
}