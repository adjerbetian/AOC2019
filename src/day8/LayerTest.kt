package day8

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class LayerTest {
    @Test
    fun getNumberOf() {
        val layer = Layer(3, 2, "123455")

        assertEquals(0, layer.getNumberOf(0))
        assertEquals(1, layer.getNumberOf(1))
        assertEquals(2, layer.getNumberOf(5))
    }
}
