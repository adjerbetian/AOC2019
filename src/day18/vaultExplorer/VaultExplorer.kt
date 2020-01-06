package day18.vaultExplorer

import day18.vault.KeyPath

interface VaultExplorer {
    fun getBestKeyPath(): KeyPath
}