package day04

import util.*

typealias Assignment = IntRange
typealias ElfPair = Pair<Assignment, Assignment>

infix fun Assignment.contained(other: Assignment): Boolean =
    first in other && endInclusive in other

infix fun Assignment.overlaps(other: Assignment): Boolean =
    first in other || endInclusive in other

fun ElfPair.totalOverlap(): Boolean =
    first contained second || second contained first

fun ElfPair.overlap(): Boolean =
    first overlaps second || second overlaps first

fun String.toAssignment(): Assignment =
    split('-').let { IntRange(it[0].toInt(), it[1].toInt()) }

fun String.toElfPair(): ElfPair =
    split(',').let { it[0].toAssignment() to it[1].toAssignment() }

fun Sequence<String>.solve(by: ElfPair.() -> Boolean): Int =
    count { it.toElfPair().by() }

fun part1(input: Sequence<String>): Int = input.solve(ElfPair::totalOverlap)

fun part2(input: Sequence<String>): Int = input.solve(ElfPair::overlap)

fun main() {
    test(::part1, 2)
    go(::part1, 305)

    test(::part2, 4)
    go(::part2, 811)
}

