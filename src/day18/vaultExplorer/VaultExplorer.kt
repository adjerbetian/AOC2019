package day18.vaultExplorer

import day18.vault.Key

interface VaultExplorer {
    fun getBestKeyPath(): Pair<List<Key>, Int>
}