package day3

import java.util.*
import kotlin.collections.HashSet
import kotlin.math.abs

class SparseGrid {
    private val positions = HashSet<Position>()

    fun has(p: Position): Boolean {
        return positions.contains(p)
    }

    fun insert(position: Position) {
        positions.add(position)
    }

    fun insert(path: Path) {
        val iterator = PathIterator(path)
        insert(iterator.getPosition())

        while (!iterator.isFinished())
            insert(iterator.getNextPosition())
    }

    fun getIntersectionWith(path: Path): List<Position> {
        val result = LinkedList<Position>()

        val iterator = PathIterator(path)
        while (!iterator.isFinished()) {
            val p = iterator.getNextPosition()
            if (has(p))
                result.add(p)
        }

        return result
    }

    override fun toString(): String {
        fun printAxisTick(v: Int): String {
            return if (v % 10 == 0) abs(((v / 10) % 10)).toString()
            else " "
        }
        fun printXAxis(xMin: Int, xMax: Int): String {
            var axis = " "
            for (x in xMin..xMax)
                axis += printAxisTick(x)
            return axis + "\n"
        }

        val xMin = positions.map { it.x }.min()!! - 1
        val xMax = positions.map { it.x }.max()!! + 1
        val yMin = positions.map { it.y }.min()!! - 1
        val yMax = positions.map { it.y }.max()!! + 1

        var result = printXAxis(xMin, xMax)

        for (y in yMax downTo yMin) {
            result += printAxisTick(y)

            for (x in xMin..xMax) {
                val p = Position(x, y)
                if (x == 0 && y == 0) result += "O"
                else if (has(p)) result += "#"
//                else if (x % 10 == 0) result += "+"
//                else if (y % 10 == 0) result += "+"
                else result += "."
            }

            result += printAxisTick(y)
            result += "\n"
        }

        result += printXAxis(xMin, xMax)

        return result
    }
}
