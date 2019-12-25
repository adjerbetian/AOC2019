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
        val (period, orePerPeriod) = findPeriod()

        var fuel = (ore / orePerPeriod) * period
        var oreLeft = ore % orePerPeriod
        while (oreLeft > 0) {
            oreLeft -= computeMinOREFor("FUEL")
            if (oreLeft >= 0) {
                fuel++
            }
        }
        return fuel
    }

    private fun findPeriod(): Pair<Int, Quantity> {
        reset()
        var ore = computeMinOREFor("FUEL")
        var period = 1
        while (stock.isNotEmpty()) {
            ore += computeMinOREFor("FUEL")
            period++
            if (period % 10000 == 0)
                println(period)
        }
        reset()
        return Pair(period, ore)
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

