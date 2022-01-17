import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun compare(times: Int, heatUpTimes: Int) {
    compareImpl(times, boxedSequence = ::benchmarkBoxed, inlinedSequence = ::benchmarkInlined, heatUpTimes)
    compareImpl(times, boxedSequence = ::benchmarkBoxedArray, inlinedSequence = ::benchmarkInlinedArray, heatUpTimes)
}
@ExperimentalTime
fun compareImpl(times: Int, boxedSequence: (times: Int) -> Sequence<Duration>, inlinedSequence: (times: Int) -> Sequence<Duration>, heatUpTimes: Int) {
    val boxedResults = mutableListOf<Duration>()
    val inlinedResults = mutableListOf<Duration>()
    (boxedSequence(heatUpTimes) zip inlinedSequence(heatUpTimes)).forEach { _ -> }
    (boxedSequence(times) zip inlinedSequence(times)).forEachIndexed { index, (boxed, inlined) ->
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
    compare(80, 20)
}