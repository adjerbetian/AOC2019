package day14

class SystemParser {
    fun parse(systemString: String): List<Reaction> {
        return systemString.split("\n").map { parseReaction(it) }
    }

    private fun parseReaction(line: String): Reaction {
        val (inputs, output) = line.split("=>").map { it.trim() }

        return Reaction(
            parseIngredient(output),
            inputs.split(", ").map { parseIngredient(it) }
        )
    }

    private fun parseIngredient(part: String): Ingredient {
        val (quantity, chemical) = part.split(" ")
        return Ingredient(quantity.toInt(), chemical)
    }
}