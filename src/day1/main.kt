package day1

fun main() {
    val fuels = moduleMasses.map { mass -> computeFuelForMass(mass) }
    val total = fuels.sum()
    println(total)
}