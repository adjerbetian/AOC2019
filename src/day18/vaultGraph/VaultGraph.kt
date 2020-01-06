package day18.vaultGraph

import day18.vault.Key
import day18.vault.TunnelElement

interface VaultGraph {
    val keys: Set<Key>
    fun getAvailableKeyDistancesFrom(element: TunnelElement, keys: Set<Key>): List<KeyDistance>
    fun getMaxDistanceToKey(element: TunnelElement, keys: Set<Key>): Int
    fun getDistancesToKeysFrom(element: TunnelElement): List<Int>
}

fun buildVaultGraph(textMap: String): VaultGraph = VaultGraphImpl(textMap)