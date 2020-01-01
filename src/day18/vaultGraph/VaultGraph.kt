package day18.vaultGraph

import day18.vault.Key
import day18.vault.TunnelElement

interface VaultGraph {
    val keys: List<Key>
    fun getAvailableKeyDistancesFrom(element: TunnelElement, keys: List<Key>): List<KeyDistance>
    fun getMaxDistanceToKey(key: Key, keys: List<Key>): Int
    fun getDistancesToKeysFrom(element: TunnelElement): List<Int>
}

fun buildVaultGraph(textMap: String): VaultGraph = VaultGraph2(textMap)
//fun buildVaultGraph(textMap: String): VaultGraph = VaultGraph1(textMap)