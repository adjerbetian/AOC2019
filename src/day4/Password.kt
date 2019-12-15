package day4

fun isPasswordValid(password: Int): Boolean {
    return hasPasswordDouble(password) && isPasswordIncreasing(password)
}

fun hasPasswordDouble(password: Int): Boolean {
    val text = password.toString()
    var i = 0
    while (i < text.length) {
        var j = i + 1
        while (j < text.length && text[j] == text[i]) j++

        if (j - i == 2) return true
        i = j
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
