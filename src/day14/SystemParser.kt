package day14

class SystemParser {
    fun parse(systemString: String): Map<Chemical, Reaction> {
        val reactions = systemString.split("\n").map { parseReaction(it) }
        val map = HashMap<Chemical, Reaction>(reactions.size)
        reactions.forEach { map[it.output.chemical] = it }
        return map
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
        return Ingredient(chemical, quantity.toLong())
    }
}