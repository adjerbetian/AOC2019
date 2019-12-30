package day18

sealed class MapElement
object Wall : MapElement()
object Entrance : MapElement()
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

fun parseKeyMap(textMap: String): HashMap<Position, MapElement> {
    val result = HashMap<Position, MapElement>()

    var x = 0
    var y = 0
    for (c in textMap) {
        if (c == '\n') {
            x = 0
            y++
        } else {
            result[Position(x++, y)] = when {
                c == '.' -> OpenPassage
                c == '@' -> Entrance
                c == '#' -> Wall
                c.isLetter() && c.isLowerCase() -> Key(c)
                c.isLetter() && c.isUpperCase() -> Door(c)
                else -> throw Error("character not recognized: $c")
            }
        }
    }

    return result
}

