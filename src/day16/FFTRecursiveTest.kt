package day16

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

internal class FFTRecursiveTest {
    @Test
    fun simple() {
        val fft = FFTRecursive("12345678")

        assertEquals("48226158", fft.apply(1))
        assertEquals("34040438", fft.apply(2))
        assertEquals("03415518", fft.apply(3))
        assertEquals("01029498", fft.apply(4))
    }

    @Test
    fun complex1() {
        val fft = FFTRecursive("80871224585914546619083218645595")

        assertEquals("24176176", fft.getValues(0, 8, 100))
    }

    @Test
    fun complex2() {
        val fft = FFTRecursive("19617804207202209144916044189917")

        assertEquals("73745418", fft.getValues(0, 8, 100))
    }

    @Test
    fun complex3() {
        val fft = FFTRecursive("69317163492948606335995924319873")

        assertEquals("52432133", fft.getValues(0, 8, 100))
    }

    @Disabled
    @Test
    fun longComplex1() {
        val fft = FFTRecursive("03036732577212944063491565474664".repeat(100))

        assertEquals("48226158", fft.getValueAt(303673, 2))
    }
}