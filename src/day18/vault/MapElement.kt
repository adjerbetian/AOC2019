package day18.vault

sealed class MapElement
object Wall : MapElement()
open class Tunnel : MapElement()

open class TunnelElement(val letter: Char) : Tunnel() {
    override fun equals(other: Any?) = if (other is TunnelElement) letter == other.letter else false
    override fun toString() = letter.toString()
    override fun hashCode() = letter.hashCode()
}

class Key(letter: Char) : TunnelElement(letter) {
    fun opens(door: Door) = door.letter.toLowerCase() == letter
    fun getDoor() = Door(letter.toUpperCase())
}

class Door(letter: Char) : TunnelElement(letter) {
    fun getKey() = Key(letter.toLowerCase())
}

object Entrance : TunnelElement('@')