package day10

class AsteroidMap(val map: String) {
    val asteroids = mutableListOf<Point>()
    val height = map.split("\n").size
    val width = map.split("\n")[0].length
    var station: Point
    var previousVaporized: Point? = null

    init {
        map.split("\n").forEachIndexed { y, row ->
            row.forEachIndexed { x, character ->
                if (character != '.')
                    asteroids.add(Point(x, y))
            }
        }
        station = bestMonitoringStation()
    }

    operator fun get(x: Int, y: Int): Boolean {
        return asteroids.contains(Point(x, y))
    }

    fun getNumberDetectableAsteroidsFrom(from: Point): Int {
        return asteroids.count { to -> areInDirectSight(from, to) } - 1
    }

    private fun areInDirectSight(from: Point, to: Point): Boolean {
        return !asteroids.any { it.isBlockingSightBetween(from, to) }
    }

    fun bestMonitoringStation(): Point {
        return asteroids.maxBy { getNumberDetectableAsteroidsFrom(it) }!!
    }

    fun vaporize(): Point {
        previousVaporized = getNextToVaporize()
        asteroids.removeIf { it == previousVaporized }
        return previousVaporized!!
    }

    private fun getNextToVaporize(): Point {
        if (previousVaporized == null) return findFirstToVaporize()

        val laser = previousVaporized!! - station
        return asteroids.minWith(Comparator(fun(p1, p2): Int {
            if (!areInDirectSight(station, p1) && !areInDirectSight(station, p2)) return 0
            if (!areInDirectSight(station, p2)) return -1
            if (!areInDirectSight(station, p1)) return 1

            val v1 = p1 - station
            val v2 = p2 - station

            if (v1.isAlignedWith(laser) && v2.isAlignedWith(laser)) return 0
            if (v2.isAlignedWith(laser)) return -1
            if (v1.isAlignedWith(laser)) return 1

            return if (v1.angleWith(laser) < v2.angleWith(laser)) 1 else -1
        }))!!
    }

    private fun findFirstToVaporize(): Point {
        val up = Vector(0, -1)
        val asteroidUp = asteroids
            .filter { (it - station).isAlignedWith(up) }
            .filter { it != station }

        return if (asteroidUp.isNotEmpty())
            asteroidUp.minBy { (it - station).norm1() }!!
        else {
            previousVaporized = station + up
            findFirstToVaporize()
        }
    }

    override fun toString(): String {
        var result = ""
        for (y in 0..height) {
            for (x in 0..width) {
                val p = Point(x, y)
                result += when {
                    p == station -> "X"
                    p == previousVaporized -> "*"
                    get(x, y) -> "#"
                    else -> " "
                }
            }
            result += "\n"
        }
        return result
    }
}
