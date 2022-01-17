import kotlin.math.sqrt
import kotlin.time.ExperimentalTime

data class Point(val x: Double, val y: Double)

fun distance(p1: Point, p2: Point) = sqrt((p1.x - p2.x).square() + (p1.y - p2.y).square())

@ExperimentalTime
fun main() {
    println("Just points")
    benchmarkBoxed(20).forEachIndexed { index, duration -> println("${index + 1}: $duration") }
    println("Arrays")
    benchmarkBoxedArray(20).forEachIndexed { index, duration -> println("${index + 1}: $duration") }
}

@ExperimentalTime
fun benchmarkBoxed(times: Int) = benchmark(times) {
    val res = DoubleArray(1024)
    for (i in 1..100_000_000) {
        val d = i.toDouble()
        val p1 = Point(1.0, d)
        val p2 = Point(d, 1.0)
        res[i and 1023] = distance(p1, p2)
    }
}

@ExperimentalTime
fun benchmarkBoxedArray(times: Int) = benchmark(times) {
    for (i in 1..10_000) {
        val n = 10_000
        val arr = Array(n) { Point(it.toDouble(), it.toDouble().square()) }
        var res = 0.0
        for (index in 1 until n) {
            res += distance(arr[index - 1], arr[index])
        }
        assert(res > 0)
    }
}