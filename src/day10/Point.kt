package day10

data class Point(val x: Int, val y: Int) {
    fun isBlockingSightBetween(from: Point, to: Point): Boolean {
        if (this == from) return false
        if (this == to) return false

        val testingVector = to - from
        val blockingVector = this - from
        if (!testingVector.isAlignedWith(blockingVector)) return false
        return blockingVector.norm1() < testingVector.norm1()
    }

    operator fun minus(p: Point) = Vector(x - p.x, y - p.y)
    operator fun plus(v: Vector) = Point(x - v.x, y + v.y)
}