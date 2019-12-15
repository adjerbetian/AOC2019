package day4

fun isPasswordValid(password: Int): Boolean {
    return hasPasswordDouble(password) && isPasswordIncreasing(password)
}

fun hasPasswordDouble(password: Int): Boolean {
    val text = password.toString()
    for (i in 0..text.length - 2) {
        if (text[i] == text[i + 1]) return true
    }
    return false
}

fun isPasswordIncreasing(password: Int): Boolean {
    val text = password.toString()
    for (i in 0..text.length - 2) {
        if (text[i].toInt() > text[i + 1].toInt()) return false
    }
    return true
}
