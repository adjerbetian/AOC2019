package day7

fun permutations(list: List<Int>): List<List<Int>> {
    if (list.isEmpty()) return emptyList()
    if (list.size == 1) return listOf(list)

    val result = mutableListOf<List<Int>>()

    list.forEachIndexed { index, element ->
        val l = list.filterIndexed { i, _ -> i != index }
        val q = permutations(l).map { listOf(element) + it }
        result.addAll(q)
    }

    return result
}