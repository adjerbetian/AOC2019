package day7

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PermutationKtTest {

    @Test
    fun permutations0() {
        assertTrue(
            permutations(emptyList()) == emptyList<List<Int>>()
        )
    }

    @Test
    fun permutations1() {
        assertTrue(
            permutations(listOf(1)) == listOf(
                listOf(1)
            )
        )
    }

    @Test
    fun permutations2() {
        assertTrue(
            permutations(listOf(1, 2)) == listOf(
                listOf(1, 2),
                listOf(2, 1)
            )
        )
    }

    @Test
    fun permutations3() {
        assertTrue(
            permutations(listOf(1, 2, 3)) == listOf(
                listOf(1, 2, 3),
                listOf(1, 3, 2),
                listOf(2, 1, 3),
                listOf(2, 3, 1),
                listOf(3, 1, 2),
                listOf(3, 2, 1)
            )
        )
    }
}