package intCode

const val MAX_SIZE = 10000

class Memory(initialValue: IntCodeProgram) {
    private var arrayState = initialValue.clone()
    private val hashState = HashMap<IntCode, IntCode>(1000)

    operator fun get(i: IntCode): IntCode {
        return if (i <= MAX_SIZE) {
            stretchMemory(i.toInt())
            arrayState[i.toInt()]
        } else {
            hashState.getOrDefault(i, 0)
        }
    }

    operator fun set(i: IntCode, value: IntCode) {
        if (i <= MAX_SIZE) {
            stretchMemory(i.toInt())
            arrayState[i.toInt()] = value
        } else {
            hashState[i] = value
        }
    }

    private fun stretchMemory(i: Int) {
        if (arrayState.size > i) return
        arrayState += LongArray(10000 - arrayState.size + 1)
    }

    fun getState() = arrayState

    override fun toString() = arrayState.toList().toString()
}