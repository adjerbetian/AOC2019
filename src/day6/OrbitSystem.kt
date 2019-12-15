package day6

typealias Planet = String
typealias OrbitMap = List<String>

class OrbitSystem(orbitStringMap: OrbitMap) {
    private val orbitMap = buildOrbitMap(orbitStringMap)

    fun getOrbitsSum(): Int {
        return orbitMap.keys.sumBy { getDistanceToCOM(it) }
    }

    fun getDistanceToCOM(planet: Planet): Int {
        var distance = 0
        var currentPlanet = planet
        while(currentPlanet != "COM") {
            distance++
            currentPlanet = getOrbitCenterOf(currentPlanet)
        }
        return distance
    }

    fun getOrbitCenterOf(planet: Planet): Planet {
        return orbitMap[planet] ?: error("Planet not found")
    }
}


fun buildOrbitMap(stringMap: OrbitMap): Map<Planet, Planet> {
    val result = HashMap<Planet, Planet>()

    stringMap.forEach { orbit ->
        val (center, satellite) = orbit.split(")")
        result[satellite] = center
    }

    return result
}
