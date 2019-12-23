package day13

import intCode.IntCodeComputer
import java.lang.Thread.sleep

sealed class Tile(val x: Int, val y: Int, val character: Char) {
    override fun toString() = character.toString()
}

class EmptyTile(x: Int, y: Int) : Tile(x, y, ' ')
class WallTile(x: Int, y: Int) : Tile(x, y, '█')
class BlockTile(x: Int, y: Int) : Tile(x, y, '▒')
class PaddleTile(x: Int, y: Int) : Tile(x, y, '▀')
class BallTile(x: Int, y: Int) : Tile(x, y, '●')

class Game {
    val tiles = mutableListOf<Tile>()
    var score = 0

    fun linkToComputer(computer: IntCodeComputer, verbose: Boolean = false) {
        var i = 0
        val values = intArrayOf(0, 0, 0)
        computer.outputFunction = fun(output) {
            values[i++] = output.toInt()
            if (i < 3) return

            val (x, y, type) = values

            if (x == -1 && y == 0)
                score = type
            else
                addTile(
                    when (type) {
                        0 -> EmptyTile(x, y)
                        1 -> WallTile(x, y)
                        2 -> BlockTile(x, y)
                        3 -> PaddleTile(x, y)
                        4 -> BallTile(x, y)
                        else -> throw Error("unknwon value $type")
                    }
                )
            i = 0
        }

        computer.onInput = {
            if(verbose) {
                print()
                sleep(500L)
            }
            when {
                getBall().x < getPaddle().x -> computer.addInput(-1)
                getBall().x > getPaddle().x -> computer.addInput(1)
                else -> computer.addInput(0)
            }
        }
    }


    private fun addTile(t: Tile) {
        tiles.removeIf { it.x == t.x && it.y == t.y }
        tiles.add(t)
    }

    fun print() = println(toString())

    override fun toString(): String {
        var result = ""
        for (y in minY()..maxY()) {
            for (x in minX()..maxX())
                result += this[x, y]
            result += "\n"
        }
        return result
    }

    private operator fun get(x: Int, y: Int): Tile {
        return tiles.find { it.x == x && it.y == y }!!
    }

    private fun minX() = tiles.map { it.x }.min()!!
    private fun minY() = tiles.map { it.y }.min()!!
    private fun maxX() = tiles.map { it.x }.max()!!
    private fun maxY() = tiles.map { it.y }.max()!!

    fun getBall() = tiles.find { it is BallTile }!!
    fun getPaddle() = tiles.find { it is PaddleTile }!!
}