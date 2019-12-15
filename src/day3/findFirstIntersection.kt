package day3

fun findFirstIntersection(firstPath: Path, secondPath: Path): Int {
    val grid = SparseGrid()
    grid.insert(firstPath)

    val intersections = grid.getIntersectionWith(secondPath)

    val distances = intersections.map { position ->
        computePathStepsUntil(firstPath, position) + computePathStepsUntil(secondPath, position)
    }
    return distances.min()!!
}

fun computePathStepsUntil(path: Path, target: Position): Int {
    var steps = 1
    val iterator = PathIterator(path)

    while (iterator.getNextPosition() != target) steps++
    return steps
}

