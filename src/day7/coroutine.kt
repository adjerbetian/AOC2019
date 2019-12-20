package day7

import kotlinx.coroutines.*

var input: Int? = null

fun main() {
    println("Start")

    GlobalScope.launch {
        delay(1000)
        input = 10
    }

    println(waitForInput())
}


fun waitForInput(): Int {
    runBlocking {
        while (input == null) {
            delay(100)
            println("waiting")
        }
    }
    return input!!
}
