package day15

class OxygenSpreader(shipMap: ShipMap) {
    private val oxygenCells = shipMap.tileMap.filter { it.value == OXYGEN }.keys.toMutableSet()
    private val emptyCells = shipMap.tileMap.filter { it.value == PATH }.keys.toMutableSet()
    private var boundary = oxygenCells.toSet()
    private var minutes = 0

    fun spreadOxygen(): Int {
        while (emptyCells.isNotEmpty()) {
            spreadOneMinute()
            minutes++
        }
        return minutes
    }

    private fun spreadOneMinute() {
        boundary = boundary.map { boundaryPosition ->
            boundaryPosition.getNeighbors().filter {
                emptyCells.contains(it)
            }
        }.flatten().toSet()
        oxygenCells.addAll(boundary)
        emptyCells.removeAll(boundary)
    }
}