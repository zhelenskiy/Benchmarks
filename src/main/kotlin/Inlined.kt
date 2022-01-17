import kotlin.math.sqrt
import kotlin.time.ExperimentalTime

fun distance(
    x1: Double, y1: Double,
    x2: Double, y2: Double,
) = sqrt((x1 - x2).square() + (y1 - y2).square())

@ExperimentalTime
fun main() {
    println("Just points")
    benchmarkInlined(20).forEachIndexed { index, duration -> println("${index + 1}: $duration") }
    println("Arrays")
    benchmarkInlinedArray(20).forEachIndexed { index, duration -> println("${index + 1}: $duration") }
}

@ExperimentalTime
fun benchmarkInlined(times: Int) = benchmark(times) {
    val res = DoubleArray(1024)
    for (i in 1..100_000_000) {
        val d = i.toDouble()
        res[i and 1023] = distance(1.0, d, d, 1.0)
    }
}

@ExperimentalTime
fun benchmarkInlinedArray(times: Int) = benchmark(times) {
    for (i in 1..10_000) {
        val n = 10_000
        val arr = DoubleArray(2 * n) { if (it and 1 == 0) it.toDouble() else it.toDouble().square() }
        var res = 0.0
        for (index in 1 until n) {
            res += distance(arr[2 * index - 2], arr[2 * index - 1], arr[2 * index], arr[2 * index + 1])
        }
        assert(res > 0)
    }
}