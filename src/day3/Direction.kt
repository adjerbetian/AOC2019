package day3

sealed class Direction {
    abstract val distance: Int
}
data class Up(override val distance: Int): Direction()
data class Down(override val distance: Int): Direction()
data class Right(override val distance: Int): Direction()
data class Left(override val distance: Int): Direction()

fun buildDirection(text: String): Direction {
    val distance = text.substring(1).toInt()
    val letter = text[0]

    if(letter == 'U') return Up(distance)
    if(letter == 'D') return Down(distance)
    if(letter == 'R') return Right(distance)
    if(letter == 'L') return Left(distance)
    throw Error("unknown letter $letter")
}
