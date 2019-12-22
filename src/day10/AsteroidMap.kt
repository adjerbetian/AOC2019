package day10

class AsteroidMap(val map: String) {
    private val asteroids = parseMap(map)

    fun getAll() = asteroids

    operator fun get(x: Int, y: Int): Boolean {
        return asteroids.contains(Point(x, y))
    }

    fun getNumberDetectableAsteroidsFrom(from: Point): Int {
        return asteroids.count { to ->
            ! asteroids.any {
                it.isBlockingSightBetween(from, to)
            }
        } - 1
    }

    fun bestMonitoringStation(): Point {
        return asteroids.maxBy { getNumberDetectableAsteroidsFrom(it) }!!
    }
}

fun parseMap(map: String): List<Point> {
    val result = mutableListOf<Point>()
    map.split("\n").forEachIndexed { y, row ->
        row.forEachIndexed { x, character ->
            if (character == '#')
                result.add(Point(x, y))
        }
    }
    return result
}