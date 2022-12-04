package day03

import util.*

typealias Item = Char
typealias Rucksack = String
typealias Group = List<Rucksack>

fun Item.priority(): Int =
    when (this) {
        in 'a'..'z' -> code - 'a'.code + 1
        in 'A'..'Z' -> code - 'A'.code + 27
        else -> error("")
    }

fun Group.priority(): Int =
    map(Rucksack::toSet).reduce { acc, s -> acc intersect s }.single().priority()

fun Rucksack.priority(): Int =
    listOf(substring(0, length / 2), substring(length / 2)).priority()

fun part1(input: Sequence<String>): Int =
    input.map(Rucksack::priority).sum()

fun part2(input: Sequence<String>): Int =
    input.chunked(3).map(Group::priority).sum()

fun main() {

    test(::part1, 157)
    go(::part1, 8252)

    test(::part2, 70)
    go(::part2, 2828)
}

