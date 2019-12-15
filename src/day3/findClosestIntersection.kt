package day3

fun findClosestIntersection(firstPath: Path, secondPath: Path): Int {
    val grid = SparseGrid()
    grid.insert(firstPath)
    val intersections = grid.getIntersectionWith(secondPath)

    val distances = intersections.map { it.getManhattanDistance() }
    return distances.min()!!
}
