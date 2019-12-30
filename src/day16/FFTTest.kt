package day16

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled

internal class FFTTest {
    @Test
    fun simple() {
        val fft = FFT("12345678")

        assertEquals("48226158", fft.apply())
        assertEquals("34040438", fft.apply())
        assertEquals("03415518", fft.apply())
        assertEquals("01029498", fft.apply())
    }

    @Test
    fun complex1() {
        val fft = FFT("80871224585914546619083218645595")

        repeat(100) { fft.apply() }

        assertEquals("24176176", fft.getCurrent().substring(0, 8))
    }

    @Test
    fun complex2() {
        val fft = FFT("19617804207202209144916044189917")

        repeat(100) { fft.apply() }

        assertEquals("73745418", fft.getCurrent().substring(0, 8))
    }

    @Test
    fun complex3() {
        val fft = FFT("69317163492948606335995924319873")

        repeat(100) { fft.apply() }

        assertEquals("52432133", fft.getCurrent().substring(0, 8))
    }

    @Disabled
    @Test
    fun longComplex1() {
        val fft = FFT("03036732577212944063491565474664".repeat(10000))

        repeat(100) { fft.apply2() }

        assertEquals("84462026", fft.getCurrentWithOffset())
    }
}