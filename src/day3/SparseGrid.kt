package day3

import java.util.*
import kotlin.collections.HashSet

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
}
