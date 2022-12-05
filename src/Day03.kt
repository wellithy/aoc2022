package day03

import util.*

@JvmInline
value class Item(val char: Char) {
    fun priority(): Int =
        when (char) {
            in 'a'..'z' -> (char - 'a') + 1
            in 'A'..'Z' -> (char - 'A') + 27
            else -> error("")
        }
}

fun Set<Item>.priority(): Int = single().priority()

@JvmInline
value class Rucksack(val items: List<Item>) {
    fun asSet(): Set<Item> =
        items.toSet()

    private fun sub(from: Int = 0, to: Int = items.size): Set<Item> =
        items.subList(from, to).toSet()

    fun priority(): Int =
        (items.size / 2).let { sub(to = it) intersect sub(from = it) }.priority()

    companion object {
        fun of(line: String): Rucksack =
            line.toCharArray().map(::Item).let(::Rucksack)
    }
}

@JvmInline
value class Group(val rucksacks: List<Rucksack>) {
    init {
        require(rucksacks.size == SIZE)
    }

    fun priority(): Int =
        rucksacks.map(Rucksack::asSet).reduce { acc, s -> acc intersect s }.priority()

    companion object {

        const val SIZE: Int = 3

        fun of(lines: List<String>): Group =
            lines.map(Rucksack::of).let(::Group)
    }
}


fun part1(input: Sequence<String>): Int =
    input.map(Rucksack::of).map(Rucksack::priority).sum()

fun part2(input: Sequence<String>): Int =
    input.chunked(Group.SIZE).map(Group::of).map(Group::priority).sum()

fun main() {

    test(::part1, 157)
    go(::part1, 8252)

    test(::part2, 70)
    go(::part2, 2828)
}
