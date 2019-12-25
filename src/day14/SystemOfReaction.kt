package day14

import java.lang.Long.min

typealias Quantity = Long
typealias Chemical = String

data class Ingredient(val chemical: Chemical, val quantity: Quantity) {
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
    private val reactions: Map<Chemical, Reaction> = SystemParser().parse(systemString)
    private val stock = Stock(reactions.keys)

    fun computeMinOREFor(chemical: Chemical): Quantity {
        return computeMinOREFor(Ingredient(chemical, 1))
    }

    private fun computeMinOREFor(ingredient: Ingredient): Quantity {
        if (ingredient.chemical == "ORE") return ingredient.quantity

        var quantityToProduce = ingredient.quantity
        val quantityFromStock = min(stock[ingredient.chemical], quantityToProduce)

        quantityToProduce -= quantityFromStock
        stock[ingredient.chemical] -= quantityFromStock

        if (quantityToProduce < 0) throw Error("not possible")
        if (quantityToProduce == 0L) return 0

        val reaction = findReactionFor(ingredient.chemical)
        val timesToRunReaction =
            quantityToProduce / reaction.output.quantity + if (quantityToProduce % reaction.output.quantity == 0L) 0 else 1

        val result = reaction.ingredients
            .map { computeMinOREFor(Ingredient(it.chemical, it.quantity * timesToRunReaction)) }
            .sum()

        stock[ingredient.chemical] += reaction.output.quantity * timesToRunReaction - quantityToProduce

        return result
    }

    private fun findReactionFor(chemical: Chemical): Reaction {
        return reactions[chemical] ?: error("No reaction for $chemical")
    }

    fun reset() {
        stock.reset()
    }

    fun computeMaxFuelWith(ore: Quantity): Quantity {
        var minFuel = 0L
        var maxFuel = 1L

        while (computeMinOREFor(Ingredient("FUEL", maxFuel)) <= ore) {
            maxFuel *= 2
            reset()
        }
        reset()

        while (maxFuel - minFuel > 1) {
            val fuel = (minFuel + maxFuel) / 2
            val oreTemp = computeMinOREFor(Ingredient("FUEL", fuel))
            reset()

            if (oreTemp > ore)
                maxFuel = fuel
            else
                minFuel = fuel
        }
        return minFuel
    }
}

class Stock(chemicals: Set<Chemical>) {
    private val stock: HashMap<Chemical, Quantity> = HashMap()

    init {
        for (chemical in chemicals)
            stock[chemical] = 0
    }

    operator fun get(chemical: Chemical): Quantity = stock[chemical]!!

    operator fun set(chemical: Chemical, quantity: Quantity) {
        stock[chemical] = quantity
    }

    fun add(chemical: Chemical, quantity: Quantity) {
        this[chemical] += quantity
    }

    fun reset() {
        stock.keys.forEach { chemical -> stock[chemical] = 0 }
    }

    fun isNotEmpty(): Boolean {
        return stock.values.sum() > 0
    }
}

