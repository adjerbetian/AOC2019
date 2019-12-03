package day1

fun main() {
    val result1 = moduleMasses.map { mass -> computeFuelForMass(mass) }.sum()
    println("result 1: $result1")

    val result2 = moduleMasses.map { mass -> computeFuelForModule(mass) }.sum()
    println("result 2: $result2")
}