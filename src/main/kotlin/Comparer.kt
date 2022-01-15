import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun compare(times: Int) {
    val boxedResults = mutableListOf<Duration>()
    val inlinedResults = mutableListOf<Duration>()
    (benchmarkBoxed(times) zip benchmarkInlined(times)).forEachIndexed { index, (boxed, inlined) ->
        println("${index + 1}: boxed = $boxed, inlined = $inlined")
        boxedResults.add(boxed)
        inlinedResults.add(inlined)
    }
    val boxedAverage = boxedResults.average()
    val inlinedAverage = inlinedResults.average()
    println("Average: boxed = $boxedAverage, inlined = $inlinedAverage")
    println("Speed up: ${boxedAverage / inlinedAverage}")
}

private fun List<Duration>.average() =
    reduce { a, b -> a + b } / size

@ExperimentalTime
fun main() {
    compare(500)
}