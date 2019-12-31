package day18.vaultGraph

import day18.vault.Key

data class KeyDistance(val key: Key, val distance: Int) {
    override fun toString() = "$key: $distance"
}