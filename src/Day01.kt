package day01

import util.*

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

fun main() {
    test(::part1, 24000)
    go(::part1, 71471)

    test(::part2, 45000)
    go(::part2, 211189)
}
