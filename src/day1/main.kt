package day1

fun main() {
    val fuels = moduleMasses.map { mass -> computeModuleFuel(mass) }
    val total = fuels.sum()
    println(total)
}