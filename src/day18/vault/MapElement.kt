package day18.vault

sealed class MapElement
object Wall : MapElement()
object OpenPassage : MapElement()

data class Key(val letter: Char) : MapElement() {
    fun opens(door: Door) = door.letter.toLowerCase() == letter
    fun getDoor() = Door(letter.toUpperCase())

    override fun toString() = letter.toString()
}

data class Door(val letter: Char) : MapElement() {
    override fun toString() = letter.toString()
    fun getKey() = Key(letter.toLowerCase())
}
