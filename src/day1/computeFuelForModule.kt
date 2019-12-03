package day1

fun computeFuelForModule(mass: Int): Int {
    var totalFuel = 0
    var fuel = computeFuelForMass(mass)
    while (fuel > 0) {
        totalFuel += fuel
        fuel = computeFuelForMass(fuel)
    }
    return totalFuel
}