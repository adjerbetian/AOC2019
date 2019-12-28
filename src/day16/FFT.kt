package day16

import kotlin.math.abs

// a_k = input_k
// b_l =     sum_(k=0..N) { p_(l,k) * a_k }
// c_m =     sum_(l=0..N) { p_(m,l) * b_l }
//     =     sum_(l=0..N) { p_(l,k) * sum_(k=0..N) { p_(m,l) * a_k }}
//     =   sum_(k,l=0..N) { p_(m,l) * p_(l,k) * a_k }
// d_n = sum_(k,l,m=0..N) { p_(n,m) * p_(m,l) * p_(l,k) * a_k }
//     = sum_(k,l,m=0..N) { p_(n,m,l,k) * a_k }


class FFT(val input: String) {
    private var current = input.map { it.toString().toInt() }

    fun apply(): String {
        current = current.indices.map { abs(apply(it)) % 10 }
        return getCurrent()
    }

    private fun apply(l: Int): Int {
        return current.mapIndexed { k, ak -> p(k, l) * ak }.sum()
    }

    private fun p(i: Int, j: Int): Int {
        val index = ((i + 1) / (j + 1)) % 4
        return arrayOf(0, 1, 0, -1)[index]
    }

    fun getCurrent(): String {
        return current.joinToString("")
    }

    fun getCurrentWithOffset(): String {
        val offset = input.substring(0, 7).toInt()
        return getCurrent().substring(offset, offset + 8)
    }
}