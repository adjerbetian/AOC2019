package day6

typealias Planet = String
typealias OrbitMap = List<String>

class OrbitSystem(orbitStringMap: OrbitMap) {
    private val orbitMap = buildOrbitMap(orbitStringMap)

    fun getOrbitsSum(): Int {
        return orbitMap.keys.sumBy { getDistanceToCOM(it) }
    }

    fun getDistanceToCOM(planet: Planet): Int {
        return getPathToCOM(planet).size - 1
    }

    fun getOrbitCenterOf(planet: Planet): Planet {
        return orbitMap[planet] ?: error("Planet not found")
    }

    fun getPathToCOM(planet: Planet): List<Planet> {
        return getPath(from = planet, to = "COM")
    }

    fun getFirstCommonAncestor(p1: Planet, p2: Planet): Planet {
        val path1 = getPathToCOM(p1)
        val path2 = getPathToCOM(p2)

        for(ancestor in path1) {
            if(path2.contains(ancestor)) {
                return ancestor
            }
        }
        throw Error("No common ancestor")
    }

    fun getPath(from: Planet, to: Planet): List<Planet> {
        var currentPlanet = from
        val result = mutableListOf(currentPlanet)
        while(currentPlanet != to) {
            currentPlanet = getOrbitCenterOf(currentPlanet)
            result.add(currentPlanet)
        }
        return result
    }

    fun getNOrbitalTransfers(from: Planet, to: Planet): Int {
        val ancestor = getFirstCommonAncestor(from, to)
        return getPath(from, ancestor).size - 1 + getPath(to, ancestor).size - 1
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
