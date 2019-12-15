package day4

fun main() {
    var n = 0
    for(i in 183564..657474) {
        if(isPasswordValid(i)) n++
    }
    println("Number of valid passowrds: $n")
}
