package day14

typealias Chemical = String

data class Ingredient(val quantity: Int, val chemical: Chemical) {
    override fun toString(): String {
        return "$quantity $chemical"
    }
}

data class Reaction(val output: Ingredient, val ingredients: List<Ingredient>) {
    override fun toString(): String {
        return ingredients.joinToString(", ") + " => " + output.toString()
    }
}


class SystemOfReaction(systemString: String) {
    private val reactions: List<Reaction> = SystemParser().parse(systemString)
    private val leftOvers = mutableListOf<Chemical>()

    fun computeMinOREFor(chemical: Chemical): Int {
        if (chemical == "ORE") return 1
        if (leftOvers.contains(chemical)) {
            leftOvers.remove(chemical)
            return 0
        }

        val reaction = findReactionFor(chemical)
        repeat(reaction.output.quantity - 1) { leftOvers.add(chemical) }

        return reaction.ingredients.sumBy { computeMinOREFor(it) }
    }

    private fun computeMinOREFor(ingredient: Ingredient): Int {
        return (1..ingredient.quantity).sumBy { computeMinOREFor(ingredient.chemical) }
    }

    private fun findReactionFor(chemical: Chemical): Reaction {
        return reactions.find { it.output.chemical == chemical }!!
    }

    fun reset() {
        leftOvers.clear()
    }
}

