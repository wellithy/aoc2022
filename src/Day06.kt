package day06

import util.*

const val PACKET_SIZE = 4
const val MESSAGE_SIZE = 14

fun String.startOfMarker(size: Int): Int =
    asSequence().windowed(size, 1).indexOfFirst { it.toSet().size == size } + size

fun part1(input: Sequence<String>): List<Int> = input.map { it.startOfMarker(PACKET_SIZE) }.toList()
fun part2(input: Sequence<String>): List<Int> = input.map { it.startOfMarker(MESSAGE_SIZE) }.toList()


fun main() {
    test(::part1, listOf(7, 5, 6, 10, 11))
    go(::part1, listOf(1093))

    test(::part2, listOf(19, 23, 23, 29, 26))
    go(::part2, listOf(3534))
}

