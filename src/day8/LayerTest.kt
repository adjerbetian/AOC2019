package day8

import org.junit.jupiter.api.Test
import kotlin.test.*

internal class LayerTest {
    @Test
    fun getNumberOf() {
        val layer = Layer(3, 2, "123455")

        assertEquals(0, layer.getNumberOf(0))
        assertEquals(1, layer.getNumberOf(1))
        assertEquals(2, layer.getNumberOf(5))
    }

    @Test
    fun equals() {
        val layer1 = Layer(2, 2, "1234")
        val layer2 = Layer(2, 2, "1234")
        val layer3 = Layer(2, 2, "1235")

        assertEquals(layer1, layer2)
        assertTrue(layer1.equals("1234"))
        assertNotEquals(layer1, layer3)
        assertFalse(layer1.equals("1235"))
    }

    @Test
    fun combiner() {
        val layer1 = Layer(2, 2, "0222")
        val layer2 = Layer(2, 2, "1122")
        val layer3 = Layer(2, 2, "2212")
        val layer4 = Layer(2, 2, "0000")

        assertEquals(Layer(2, 2, "0122"), layer1.mergeDownWith(layer2))
        assertEquals(Layer(2, 2, "0112"), layer1.mergeDownWith(layer2).mergeDownWith(layer3))
        assertEquals(Layer(2, 2, "0110"), layer1.mergeDownWith(layer2).mergeDownWith(layer3).mergeDownWith(layer4))
    }

    @Test
    fun toCode() {
        val code = "012321"
        val layer = Layer(3, 2, "012321")

        assertEquals(code, layer.getCode())
    }
}
