package day8

import org.junit.jupiter.api.Test
import kotlin.test.*

internal class SpaceImageTest {
    @Test
    fun spaceImage() {
        val image = SpaceImage(3, 2, "002222" + "111222" + "212112" + "000000")

        assertEquals(Layer(3, 2, "001110"), image.merge())
    }
}