package day8

class SpaceImage(width: Int, height: Int, format: String) {
    val layers = mutableListOf<Layer>()

    init {
        var i = 0
        val layerSize = width * height

        while (i < format.length) {
            val layerString = format.substring(i, i + layerSize)
            layers.add(Layer(width, height, layerString))
            i += layerSize
        }
    }

    fun findMinLayerBy(matcher: (layer: Layer) -> Int): Layer {
        return layers.minBy(matcher)!!
    }

    fun merge(): Layer {
        return layers.reduce { result, layer -> result.mergeDownWith(layer) }
    }
}
