package day04

import util.*

@JvmInline
value class Elf(private val assignment: IntRange) {
    infix fun contained(other: Elf): Boolean =
        assignment.first in other.assignment && assignment.last in other.assignment

    infix fun overlaps(other: Elf): Boolean =
        assignment.first in other.assignment || assignment.last in other.assignment

    companion object {
        fun of(str: String): Elf =
            str.split('-').map { it.toInt() }.let { (first, last) -> Elf(IntRange(first, last)) }

    }

}

@JvmInline
value class ElfPair(private val pair: Pair<Elf, Elf>) {
    fun totalOverlap(): Boolean =
        pair.first contained pair.second || pair.second contained pair.first

    fun overlap(): Boolean =
        pair.first overlaps pair.second || pair.second overlaps pair.first

    companion object {
        fun of(str: String): ElfPair =
            str.split(',').map { Elf.of(it) }.let { (first, second) -> ElfPair(first to second) }
    }

}

fun Sequence<String>.solve(by: ElfPair.() -> Boolean): Int =
    count { ElfPair.of(it).by() }

fun part1(input: Sequence<String>): Int = input.solve(ElfPair::totalOverlap)

fun part2(input: Sequence<String>): Int = input.solve(ElfPair::overlap)

fun main() {
    test(::part1, 2)
    go(::part1, 305)

    test(::part2, 4)
    go(::part2, 811)
}

