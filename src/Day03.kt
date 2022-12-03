package day03

import util.*

typealias Item = Char
typealias Rucksack = String
typealias Group = List<String>

fun Item.priority(): Int =
    when (this) {
        in 'a'..'z' -> code - 'a'.code + 1
        in 'A'..'Z' -> code - 'A'.code + 27
        else -> error("")
    }

fun List<String>.common(): Char =
    asSequence().map(String::toSet).reduce { acc, s -> acc intersect s }.single()

fun Rucksack.common(): Item =
    listOf(substring(0, length / 2), substring(length / 2)).common()

fun part1(input: Sequence<String>): Int =
    input.map(Rucksack::common).sumOf(Item::priority)

fun part2(input: Sequence<String>): Int =
    input.chunked(3).map(Group::common).sumOf(Item::priority)

fun main() {

    test(::part1, 157)
    go(::part1, 8252)

    test(::part2, 70)
    go(::part2, 2828)
}

