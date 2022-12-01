import kotlin.io.path.useLines

fun main() {
    fun Sequence<String>.toCalories(): Sequence<Int> = sequence {
        var calories = 0
        forEach {
            if (it.isEmpty()) {
                yield(calories)
                calories = 0
            } else calories += it.toInt()
        }
        yield(calories)
    }

    fun part1(input: Sequence<String>): Int = input.toCalories().max()

    fun part2(input: Sequence<String>): Int = input.toCalories().top(3).sum()

    go(::part1, 24000, TEST)
    go(::part1, 71471)

    go(::part2, 45000, TEST)
    go(::part2, 211189)
}
