package day18.vaultExplorer

import day18.vault.Key

interface VaultExplorer {
    var bestPathLength: Int
    fun getBestKeyPath(): Pair<List<Key>, Int>
}