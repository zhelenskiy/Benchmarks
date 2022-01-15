import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@ExperimentalTime
inline fun benchmark(times: Int, crossinline action: () -> Unit) = sequence {
    repeat(times) {
        yield(measureTime(action))
    }
}

fun Double.square() = this * this