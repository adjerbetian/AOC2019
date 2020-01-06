package day18.vault

import kotlin.math.ceil

fun splitVaultMaps(textMap: String): List<String> {
    val height = textMap.split("\n").size
    val width = textMap.length / height

    fun extractMap(up: Boolean, right: Boolean): String {
        val w = ceil(width / 2.0).toInt()
        val h = ceil(height / 2.0).toInt()

        val minX = if (right) w - 1 else 0
        val maxX = if (right) width else w
        val minY = if (up) h - 1 else 0
        val maxY = if (up) height else h

        return textMap
            .split("\n")
            .subList(minY, maxY)
            .map { row -> row.substring(minX, maxX) }
            .joinToString("\n")
    }

    return listOf(
        extractMap(up = false, right = false),
        extractMap(up = false, right = true),
        extractMap(up = true, right = false),
        extractMap(up = true, right = true)
    )
}

fun parseVaultMap(textMap: String): Map<Position, MapElement> {
    return textMap
        .split("\n")
        .mapIndexed { y, row ->
            row.mapIndexed { x, c ->
                Pair(
                    Position(x, y),
                    parseChar(c)
                )
            }
        }
        .flatten()
        .associate { it }
}

private fun parseChar(c: Char) = when {
    c == '#' -> Wall
    c == '.' -> Tunnel
    c == '@' -> Entrance
    c.isLetter() && c.isLowerCase() -> Key(c)
    c.isLetter() && c.isUpperCase() -> Door(c)
    else -> throw Error("character not recognized: $c")
}