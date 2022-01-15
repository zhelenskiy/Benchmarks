import kotlin.math.sqrt
import kotlin.time.ExperimentalTime

fun distance(
    x1: Double, y1: Double,
    x2: Double, y2: Double,
) = sqrt((x1 - x2).square() + (y1 - y2).square())

@ExperimentalTime
fun main() {
    benchmarkInlined(20).forEachIndexed { index, duration -> println("${index + 1}: $duration") }
}

@ExperimentalTime
fun benchmarkInlined(times: Int) = benchmark(times) {
    val res = DoubleArray(1024)
    for (i in 1..100_000_000) {
        val d = i.toDouble()
        res[i and 1023] = distance(1.0, d, d, 1.0)
    }
}