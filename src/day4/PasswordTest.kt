package day4

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PasswordTest {
    @Test
    fun passwordShouldHaveADouble() {
        assertFalse(isPasswordValid(123456))
        assertTrue(isPasswordValid(112345))
    }

    @Test
    fun passwordShouldHaveIncreasingNumbers() {
        assertTrue(isPasswordValid(113456))
        assertFalse(isPasswordValid(112354))
    }

    @Test
    fun passwordShouldNotAcceptDoublesOfLargerGroup() {
        assertTrue(isPasswordValid(112233))
        assertFalse(isPasswordValid(123444))
        assertTrue(isPasswordValid(111122))
    }

}
